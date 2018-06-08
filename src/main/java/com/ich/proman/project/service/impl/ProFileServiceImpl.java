package com.ich.proman.project.service.impl;

import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProFileMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProFile;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProFileService;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProFileServiceImpl implements ProFileService {

    @Autowired
    private ProFileMapper fileMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addFile(ProFile file) {
        if(ObjectHelper.isEmpty(file.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(file.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(file.getSuffix())||ObjectHelper.isEmpty(file.getFilename()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传文件！");
        if(ObjectHelper.isEmpty(file.getFile()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传文件！");
        List<ProFile> list = fileMapper.selectByName(file.getProjectid(),file.getFilename());
        if(ObjectHelper.isNotEmpty(list)) return new HttpResponse(HttpResponse.HTTP_ERROR,"文件名称重复！");
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        file.setId(IDUtils.createUUId());
        file.setUserid(employee.getEmployeeId());
        file.setUsername(employee.getEmployeeName());
        file.setCreatetime(day);
        file.setGroupid(file.getId());
        file.setGroupstatus(Constant.STATUS_NORMAL);
        fileMapper.insert(file);
        List<ProRole> roles = roleService.findOnlyRoleByPid(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),file.getFilename()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_FILE_CREATE,message_args),PMessage.PROJECT_FILE_CREATE,file.getFilename());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editFile(String id, String file, String iterationcauses) {
        ProFile proFile = this.fileMapper.selectById(id);
        if(ObjectHelper.isEmpty(proFile.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(proFile.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        fileMapper.updateToHis(id);
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        proFile.setId(IDUtils.createUUId());
        proFile.setFile(file);
        proFile.setIterationcauses(iterationcauses);
        proFile.setUserid(employee.getEmployeeId());
        proFile.setUsername(employee.getEmployeeName());
        proFile.setCreatetime(day);
        fileMapper.insert(proFile);
        List<ProRole> roles = roleService.findOnlyRoleByPid(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),proFile.getFilename()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_FILE_ITE,message_args),PMessage.PROJECT_FILE_ITE,proFile.getFilename());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse delFile(String id, String deletecauses) {
        ProFile proFile = this.fileMapper.selectById(id);
        if(ObjectHelper.isEmpty(proFile.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(proFile.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        fileMapper.updateToDis(id,deletecauses);
        List<ProRole> roles = roleService.findOnlyRoleByPid(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),proFile.getFilename()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_FILE_DISABLE,message_args),PMessage.PROJECT_FILE_DISABLE,proFile.getFilename());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProFile> findListByMid(String modularid) {
        return fileMapper.selectListByMid(modularid);
    }

    @Override
    public List<ProFile> findNormalListByMid(String modularid) {
        return fileMapper.selectNormalListByMid(modularid);
    }

    @Override
    public Integer findCountNormalListByMid(String modularid) {
        return fileMapper.selectCountNormalListByMid(modularid);
    }

    @Override
    public List<ProFile> findNormalListByPid(String projectid) {
        return fileMapper.selectNormalListByPid(projectid);
    }

    @Override
    public Integer findCountNormalListByPid(String projectid) {
        return fileMapper.selectCountNormalListByPid(projectid);
    }

    @Override
    public List<ProFile> findNormalListBySource(String projectid,String source, String sourceid) {
        return fileMapper.selectNormalListBySource(projectid,source,sourceid);
    }

    @Override
    public Integer findCountNormalListBySource(String projectid,String source, String sourceid) {
        return fileMapper.selectCountNormalListBySource(projectid,source,sourceid);
    }

    @Override
    public List<ProFile> findVersions(String id) {
        return fileMapper.selectVersions(id);
    }
}
