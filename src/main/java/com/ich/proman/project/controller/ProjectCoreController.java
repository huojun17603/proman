package com.ich.proman.project.controller;

import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProjectCoreController extends PromanController {

    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/new")
    @ResponseBody
    @Link(name = "项目消息-未读消息数量",code = "admin-pro-center-new",parent = "admin-pro-center-index", level = Link.LEVEL_NONE)
    public String addNewProject(Project project,String callback){
        HttpResponse response = projectCoreService.addNewProject(project);
        return callback(callback,response);
    }

}
