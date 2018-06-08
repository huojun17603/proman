package com.ich.proman.project.service.impl;

import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProModularService;
import com.ich.proman.project.service.ProRoleService;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProjectCoreServiceImpl implements ProjectCoreService {

    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProModularService modularService;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addNewProject(Project project) {
        if(ObjectHelper.isEmpty(project.getTitle())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请填写项目名称！");
        if(ObjectHelper.isEmpty(project.getVersion())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请填写项目版本号！");
        Project pro = projectCoreMapper.selectByTitleAndVersion(project.getTitle(),project.getVersion());
        if(ObjectHelper.isNotEmpty(pro)) return new HttpResponse(HttpResponse.HTTP_ERROR,"存在相同的项目名称与版本号！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        /*/配置项目数据/*/
        Date day = new Date();
        project.setId(IDUtils.createUUId());
        project.setCreatetime(day);
        project.setIterationtime(day);
        project.setUserid(employee.getEmployeeId());
        project.setUsername(employee.getEmployeeName());
        project.setVersionid(project.getId());
        project.setStatus(Constant.STATUS_NORMAL);
        projectCoreMapper.insert(project);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editProjectTitle(String id, String title) {
        if(ObjectHelper.isEmpty(id)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!=Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(title)) return new HttpResponse(HttpResponse.HTTP_ERROR,"请填写项目名称！");
        projectCoreMapper.updateTitle(id,title);
        List<ProRole> roles = roleService.findOnlyRoleByPid(id);
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),title};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_EDIT_NAME,message_args),PMessage.PROJECT_EDIT_NAME,id);
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editProjectToDel(String id, String deletecauses) {
        if(ObjectHelper.isEmpty(id)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!=Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(deletecauses)) return new HttpResponse(HttpResponse.HTTP_ERROR,"请填写删除理由！");
        projectCoreMapper.updateProjectToDel(id,deletecauses);
        List<ProRole> roles = roleService.findOnlyRoleByPid(id);
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_EDIT_DISABLE,message_args),PMessage.PROJECT_EDIT_DISABLE,id);
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editProjectToHis(String id, String iterationcauses){
        if(ObjectHelper.isEmpty(id)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!=Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(iterationcauses)) return new HttpResponse(HttpResponse.HTTP_ERROR,"请填写标记理由！");
        projectCoreMapper.updateProjectToHis(id,iterationcauses);
        List<ProRole> roles = roleService.findOnlyRoleByPid(id);
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_EDIT_HIS,message_args),PMessage.PROJECT_EDIT_HIS,id);
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);

    }

    @Override
    public HttpResponse exeversion(String id, String version) {
        if(ObjectHelper.isEmpty(id)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!=Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project pro = projectCoreMapper.selectByVersion(id,version);
        if(ObjectHelper.isNotEmpty(pro)) return new HttpResponse(HttpResponse.HTTP_ERROR,"存在相同的项目版本号！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        Project newPro = new Project();//分离项目
        Date day = new Date();
        newPro.setId(IDUtils.createUUId());
        newPro.setVersion(version);
        newPro.setVersionid(id);
        newPro.setTitle(project.getTitle());
        newPro.setCreatetime(project.getCreatetime());
        newPro.setIterationtime(day);
        newPro.setUserid(employee.getEmployeeId());
        newPro.setUsername(employee.getEmployeeName());
        newPro.setVersionid(project.getId());
        newPro.setStatus(Constant.STATUS_NORMAL);
        projectCoreMapper.insert(newPro);
        //复制老项目的其他数据
        //TODO
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public boolean effective(String id) {
        if(ObjectHelper.isEmpty(id)) return false;
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return false;
        return true;
    }

    @Override
    public List<Project> findGroupList() {
        return projectCoreMapper.selectGroupByVersionid();
    }

    @Override
    public List<Project> findVersions(String id) {
        return projectCoreMapper.selectVersionById(id);
    }

    @Override
    public Project findById(String id) {
        return projectCoreMapper.selectById(id);
    }

    @Override
    public List<Map<String,Object>> findModularListByPid(String id) {
        return projectCoreMapper.selectModularListByPid(id);
    }

    @Override
    public List<Map<String, Object>> findModularList(ProjectQuery query) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getCatalogid()))paramMap.put("catalogid", query.getCatalogid());
        List<Map<String,Object>> list = projectCoreMapper.selectModularListByQuery(paramMap);
        return list;
    }
}
