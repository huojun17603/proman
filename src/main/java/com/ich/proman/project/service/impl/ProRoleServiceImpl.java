package com.ich.proman.project.service.impl;

import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProRoleMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProRoleService;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProRoleServiceImpl implements ProRoleService {

    @Autowired
    private ProRoleMapper roleMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private PMessageService messageService;
    @Override
    public HttpResponse addRole(ProRole role) {
        if(ObjectHelper.isEmpty(role.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"这是一个无效的项目!");
        Project project = projectCoreMapper.selectById(role.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"这是一个无效的项目!");
        if(ObjectHelper.isEmpty(role.getUserid())||ObjectHelper.isEmpty(role.getUsername())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择参与人员!");
        if(ObjectHelper.isEmpty(role.getRole())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择对应角色!");
        role.setId(IDUtils.createUUId());
        roleMapper.insert(role);
        String message_args[] = new String[]{"TODO",project.getTitle(),project.getVersion()};
        messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_ADD,message_args),PMessage.PROJECT_ROLE_ADD,project.getId());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse deleteRole(String id) {
        ProRole role = roleMapper.selectById(id);
        if(ObjectHelper.isEmpty(role)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的角色信息!");
        Project project = projectCoreMapper.selectById(role.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"这是一个无效的项目!");
        roleMapper.delete(id);
        String message_args[] = new String[]{"TODO",project.getTitle(),project.getVersion()};
        messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_DEL,message_args),PMessage.PROJECT_ROLE_DEL,project.getId());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProRole> findProRole(String projectid) {
        return roleMapper.selectByProId(projectid);
    }
}
