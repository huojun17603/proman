package com.ich.proman.project.service.impl;

import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.mapper.ProModularMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProModularService;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProModularServiceImpl implements ProModularService {

    @Autowired
    private ProModularMapper modularMapper;
    @Autowired
    private ProjectCoreService projectCoreService;

    @Override
    public HttpResponse addModular(String projectid, String modularname,Boolean isdefault) {
        Boolean effective = projectCoreService.effective(projectid);
        if(!effective) return new HttpResponse(HttpResponse.HTTP_ERROR,"这是一个无效的项目!");
        if(ObjectHelper.isEmpty(modularname)) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入模块名称!");
        ProModular modular = new ProModular();
        modular.setId(IDUtils.createUUId());
        modular.setModularname(modularname);
        modular.setProjectid(projectid);
        modular.setIsdefault(isdefault);
        modularMapper.insert(modular);
        //TODO 信息发送
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editModularName(String id, String modularname) {
        ProModular modular = modularMapper.selectById(id);
        if(ObjectHelper.isEmpty(modular)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息!");
        modularMapper.updateModularName(id,modularname);
        //TODO 信息发送
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse deleteModular(String id) {
        ProModular modular = modularMapper.selectById(id);
        if(ObjectHelper.isEmpty(modular)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息!");
        if(modular.getIsdefault()) return new HttpResponse(HttpResponse.HTTP_ERROR,"不能删除默认的模块!");
        /*/验证模块下是否存在内容/*/
        //TODO 检查
        modularMapper.delete(id);
        //TODO 信息发送
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }
}
