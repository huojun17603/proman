package com.ich.proman.project.service.impl;

import com.ich.admin.service.LocalEmployeeService;
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
        List<ProRole> list = roleMapper.selectByOnly(role.getProjectid(),role.getUserid(),role.getRole());
        if(ObjectHelper.isNotEmpty(list)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此员工已担任当前职位!");
        role.setId(IDUtils.createUUId());
        roleMapper.insert(role);
        List<ProRole> roles = findOnlyRoleByPid(project.getId());
        for(ProRole r : roles){
            if(r.getUserid().equals(role.getUserid())){//自己
                String message_args[] = new String[]{project.getTitle(),project.getVersion()};
                messageService.sendMessageToId(r.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_ADD,message_args),PMessage.PROJECT_ROLE_ADD,project.getId());
            }else{//
                String message_args[] = new String[]{project.getTitle(),project.getVersion(),role.getUsername(),ProRole.FINDROLENAME(role.getRole())};
                messageService.sendMessageToId(r.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_ADD_NOTICE,message_args),PMessage.PROJECT_ROLE_ADD_NOTICE,project.getId());
            }
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse deleteRole(String id) {
        ProRole role = roleMapper.selectById(id);
        if(ObjectHelper.isEmpty(role)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的角色信息!");
        Project project = projectCoreMapper.selectById(role.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"这是一个无效的项目!");
        List<ProRole> roles = findOnlyRoleByPid(project.getId());
        for(ProRole r : roles){
            if(r.getId().equals(id)){//自己
                String message_args[] = new String[]{project.getTitle(),project.getVersion()};
                messageService.sendMessageToId(r.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_DEL,message_args),PMessage.PROJECT_ROLE_DEL,project.getId());
            }else{//
                String message_args[] = new String[]{project.getTitle(),project.getVersion(),role.getUsername(),ProRole.FINDROLENAME(role.getRole())};
                messageService.sendMessageToId(r.getUserid(), PMessage.findTemplate(PMessage.PROJECT_ROLE_DEL_NOTICE,message_args),PMessage.PROJECT_ROLE_DEL_NOTICE,project.getId());
            }
        }
        roleMapper.delete(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProRole> findProRoles(String projectid) {
        return roleMapper.selectByProId(projectid);
    }

    @Override
    public List<ProRole> findOnlyRoleByPid(String projectid) {
        return roleMapper.selectOnlyRoleByPid(projectid);
    }

    @Override
    public List<ProRole> selectByUserid(String projectid,String userid) {
        return roleMapper.selectByUserid(projectid,userid);
    }

    @Override
    public ProRole selectByUseridAndRole(String projectid, String userid, Integer role) {
        return roleMapper.selectByUseridAndRole(projectid,userid,role);
    }


}
