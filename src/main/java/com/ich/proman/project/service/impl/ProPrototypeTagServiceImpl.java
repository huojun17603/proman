package com.ich.proman.project.service.impl;

import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProPrototypeMapper;
import com.ich.proman.project.mapper.ProPrototypeTagMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeTag;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProPrototypeTagService;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProPrototypeTagServiceImpl implements ProPrototypeTagService {

    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;
    @Autowired
    private ProPrototypeMapper prototypeMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addTag(ProPrototypeTag tag) {
        ProPrototype prototype = this.prototypeMapper.selectById(tag.getPrototypeid());
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(tag.getContent()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入内容！");
        if(ObjectHelper.isEmpty(tag.getCode()))return new HttpResponse(HttpResponse.HTTP_ERROR,"编号不可为空！");
        ProPrototypeTag prototypeTag = prototypeTagMapper.selectNormalByCode(tag.getPrototypeid(),tag.getCode());
        if(ObjectHelper.isNotEmpty(prototypeTag)) return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的编号！");
        if(ObjectHelper.isEmpty(tag.getMapx())||ObjectHelper.isEmpty(tag.getMapy()))
            return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入坐标！");

        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        tag.setId(IDUtils.createUUId());
        tag.setGroupid(tag.getId());
        tag.setCreatetime(new Date());
        tag.setGroupstatus(Constant.STATUS_NORMAL);
        tag.setUserid(employee.getEmployeeId());
        tag.setUsername(employee.getEmployeeName());
        this.prototypeTagMapper.insert(tag);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle(),tag.getCode()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_TAG_ADD,message_args),PMessage.PROJECT_PROTOTYPE_TAG_ADD,tag.getId());
        }
        Map<String,Object> data = new HashMap<>();
        data.put("id",tag.getId());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK,data);
    }

    @Override
    public HttpResponse editIteration(String id, String content,String iterationcauses) {
        ProPrototypeTag tag = prototypeTagMapper.selectById(id);
        ProPrototype prototype = this.prototypeMapper.selectById(tag.getPrototypeid());
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(content))return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入内容！");
        this.prototypeTagMapper.updateToHis(id);
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        tag.setId(IDUtils.createUUId());
        tag.setCreatetime(new Date());
        tag.setContent(content);
        tag.setIterationcauses(iterationcauses);
        tag.setUserid(employee.getEmployeeId());
        tag.setUsername(employee.getEmployeeName());
        this.prototypeTagMapper.insert(tag);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle(),tag.getCode()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_TAG_EDIT,message_args),PMessage.PROJECT_PROTOTYPE_TAG_EDIT,tag.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editMap(String id, String mapx, String mapy) {
        ProPrototypeTag tag = prototypeTagMapper.selectById(id);
        ProPrototype prototype = this.prototypeMapper.selectById(tag.getPrototypeid());
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(mapx)||ObjectHelper.isEmpty(mapy))
            return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入坐标！");
        this.prototypeTagMapper.updateMap(id,mapx,mapy);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse addTagByImport(String prototypeid,String id) {
        ProPrototypeTag tag = prototypeTagMapper.selectById(id);//被选中的标记
        List<ProPrototypeTag> tags = findVersions(id);//被选中的标记的历史标记列表
        ProPrototype prototype = this.prototypeMapper.selectById(prototypeid);//当前原型
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        ProPrototypeTag prototypeTag = prototypeTagMapper.selectNormalByCode(prototypeid,tag.getCode());
        if(ObjectHelper.isNotEmpty(prototypeTag)) return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的编号！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        tag.setId(IDUtils.createUUId());
        tag.setPrototypeid(prototypeid);
        tag.setGroupid(tag.getId());
        for(ProPrototypeTag obj : tags){
            obj.setId(IDUtils.createUUId());
            obj.setPrototypeid(prototypeid);
            obj.setGroupid(tag.getId());
            this.prototypeTagMapper.insert(obj);
        }
        this.prototypeTagMapper.insert(tag);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle(),tag.getCode()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_TAG_IMPORT,message_args),PMessage.PROJECT_PROTOTYPE_TAG_IMPORT,tag.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProPrototypeTag> findNormalListByPid(String prototypeid) {
        return prototypeTagMapper.selectNormalListByPid(prototypeid);
    }

    @Override
    public List<ProPrototypeTag> findVersions(String id) {
        return prototypeTagMapper.selectVersions(id);
    }

    @Override
    public HttpResponse addTagByImports(String prototypeid, String ids) {
        String id_arr[]  = ids.split(",");
        for (String id : id_arr){
            addTagByImport(prototypeid,id);
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse findById(String id) {
        ProPrototypeTag tag = prototypeTagMapper.selectById(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK,tag);
    }
}
