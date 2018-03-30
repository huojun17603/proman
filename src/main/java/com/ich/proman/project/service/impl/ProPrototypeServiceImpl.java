package com.ich.proman.project.service.impl;

import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.LocalEmployeeService;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProModularMapper;
import com.ich.proman.project.mapper.ProPrototypeMapper;
import com.ich.proman.project.mapper.ProPrototypeTagMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeTag;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProPrototypeService;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProPrototypeServiceImpl implements ProPrototypeService {

    @Autowired
    private ProPrototypeMapper prototypeMapper;
    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addPrototype(ProPrototype prototype) {
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(prototype.getModularid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择一个模块！");
        if(ObjectHelper.isEmpty(prototype.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"原型标题不可为空！");
        if(ObjectHelper.isEmpty(prototype.getImg()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传原型图！");
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        prototype.setId(IDUtils.createUUId());
        prototype.setUserid(employee.getEmployeeId());
        prototype.setUsername(employee.getEmployeeName());
        prototype.setCreatetime(day);
        prototype.setGroupid(prototype.getId());
        prototype.setGroupstatus(Constant.STATUS_NORMAL);
        prototypeMapper.insert(prototype);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_ADD,message_args),PMessage.PROJECT_PROTOTYPE_ADD,prototype.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPrototypeTitle(String id, String title) {
        ProPrototype prototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        prototypeMapper.updateTitle(id,title);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle(),title};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_EDIT_TITLE,message_args),PMessage.PROJECT_PROTOTYPE_EDIT_TITLE,prototype.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPrototypeToImg(String id, String img, String iterationcauses,Boolean imports) {
        ProPrototype oprototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(oprototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(oprototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(img))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传原型图！");
        prototypeMapper.updateToHis(id);
        ProPrototype prototype = new ProPrototype();
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        prototype.setId(IDUtils.createUUId());
        prototype.setTitle(oprototype.getTitle());
        prototype.setProjectid(oprototype.getProjectid());
        prototype.setModularid(oprototype.getModularid());
        prototype.setImg(img);
        prototype.setUserid(employee.getEmployeeId());
        prototype.setUsername(employee.getEmployeeName());
        prototype.setCreatetime(day);
        prototype.setGroupid(oprototype.getId());
        prototype.setGroupstatus(Constant.STATUS_NORMAL);
        prototype.setIterationcauses(iterationcauses);
        prototypeMapper.insert(prototype);
        //引入
        List<ProPrototypeTag> tagss = this.prototypeTagMapper.selectNormalListByPid(oprototype.getId());
        for(ProPrototypeTag tag : tagss){
            List<ProPrototypeTag> tags = prototypeTagMapper.selectVersions(tag.getGroupid());//被选中的标记的历史标记列表
            tag.setId(IDUtils.createUUId());
            tag.setPrototypeid(prototype.getId());
            tag.setGroupid(tag.getId());
            this.prototypeTagMapper.insert(tag);
            for(ProPrototypeTag obj : tags){
                if(!tag.getCode().equals(obj.getCode())) {
                    obj.setId(IDUtils.createUUId());
                    obj.setPrototypeid(prototype.getId());
                    obj.setGroupid(tag.getId());
                    this.prototypeTagMapper.insert(obj);
                }
            }
        }
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_EDIT_IMG,message_args),PMessage.PROJECT_PROTOTYPE_EDIT_IMG,prototype.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPrototypeToDel(String id, String deletecauses) {
        ProPrototype prototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        prototypeMapper.updateToDel(id,deletecauses);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_DEL,message_args),PMessage.PROJECT_PROTOTYPE_DEL,prototype.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProPrototype> findListByMid(String mid) {
        return prototypeMapper.selectListByMid(mid);
    }

    @Override
    public List<ProPrototype> findNormalListByMid(String mid) {
        return prototypeMapper.selectNormalListByMid(mid);
    }

    @Override
    public Integer findCountNormalListByMid(String mid) {
        return prototypeMapper.selectCountNormalListByMid(mid);
    }

    @Override
    public List<ProPrototype> findVersions(String id) {
        return prototypeMapper.selectVersions(id);
    }
}
