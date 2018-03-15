package com.ich.proman.project.service.impl;

import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.Constant;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectCoreServiceImpl implements ProjectCoreService {

    @Autowired
    private ProjectCoreMapper projectCoreMapper;

    @Override
    public HttpResponse addNewProject(Project project, List<ProRole> roleList) {
        
        return null;
    }

    @Override
    public HttpResponse editProjectTitle(String id, String title) {
        return null;
    }

    @Override
    public HttpResponse editProjectToDel(String id, String deletecauses) {
        return null;
    }

    @Override
    public HttpResponse editProjectToHis(String id, String iterationcauses) {
        return null;
    }

    @Override
    public HttpResponse exeversion(String id, String version) {
        return null;
    }

    @Override
    public boolean effective(String id) {
        if(ObjectHelper.isEmpty(id)) return false;
        Project project = projectCoreMapper.selectById(id);
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return false;
        return true;
    }
}
