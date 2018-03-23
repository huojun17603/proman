package com.ich.proman.project.service.impl;

import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProModularMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.*;
import com.ich.proman.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProModularServiceImpl implements ProModularService {

    @Autowired
    private ProModularMapper modularMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private ProPrototypeService prototypeService;
    @Autowired
    private ProDesignService designService;
    @Autowired
    private ProTaskService taskService;
    @Autowired
    private ProTestService testService;
    @Autowired
    private ProBugService bugService;
    @Autowired
    private ProFileService fileService;

    @Override
    public HttpResponse addModular(String projectid, String modularname,Boolean isdefault) {
        if(ObjectHelper.isEmpty(projectid)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(projectid);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(modularname)) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入模块名称!");
        List<ProModular> list = modularMapper.selectByName(modularname);
        if(ObjectHelper.isNotEmpty(list))return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的模块名称!");
        ProModular modular = new ProModular();
        modular.setId(IDUtils.createUUId());
        modular.setModularname(modularname);
        modular.setProjectid(projectid);
        modular.setIsdefault(isdefault);
        modularMapper.insert(modular);
        List<ProRole> roles = roleService.findProRole(projectid);
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),modularname};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_MODULAR_CREATE,message_args),PMessage.PROJECT_MODULAR_CREATE,modular.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editModularName(String id, String modularname) {
        ProModular modular = modularMapper.selectById(id);
        if(ObjectHelper.isEmpty(modular)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息!");
        modularMapper.updateModularName(id,modularname);
        Project project = projectCoreMapper.selectById(modular.getProjectid());
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),modular.getModularname(),modularname};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_MODULAR_EDIT_NAME,message_args),PMessage.PROJECT_MODULAR_EDIT_NAME,modular.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse deleteModular(String id) {
        ProModular modular = modularMapper.selectById(id);
        if(ObjectHelper.isEmpty(modular)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息!");
        if(ObjectHelper.isEmpty(modular.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(modular.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(modular.getIsdefault()) return new HttpResponse(HttpResponse.HTTP_ERROR,"不能删除默认的模块!");
        /*/验证模块下是否存在内容/*/
        List<ProPrototype> prototypes = this.prototypeService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(prototypes)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在原型信息，不可删除!");
        List<ProDesign> designs = this.designService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(designs)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在设计信息，不可删除!");
        List<ProTask> tasks = this.taskService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(tasks)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在任务信息，不可删除!");
        List<ProTest> tests = this.testService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(tests)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在用例信息，不可删除!");
        List<ProBug> bugs = this.bugService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(bugs)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在BUG信息，不可删除!");
        List<ProFile> files = this.fileService.findListByMid(id);//验证是否有原型信息
        if(ObjectHelper.isNotEmpty(files)) return new HttpResponse(HttpResponse.HTTP_ERROR,"此模块下已存在文件信息，不可删除!");
        modularMapper.delete(id);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),modular.getModularname()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_MODULAR_DEL,message_args),PMessage.PROJECT_MODULAR_DEL,modular.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public Map<String, Object> findModularDetailById(String modularid) {
        return modularMapper.selectModularDetailById(modularid);
    }
}
