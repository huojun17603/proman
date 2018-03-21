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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectCoreController extends PromanController {

    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/center")
    @Link(name = "项目-项目中心",code = "admin-project-center-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String id , String callback){
        String viewname = "project/projectCenter";
        Map<String,Object> model = new HashMap<>();
        model.put("id",id);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/grouplist")
    @ResponseBody
    @Link(name = "项目-获取项目分组列表",code = "admin-project-center-grouplist",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public List<?> grouplist(String callback){
        List<Project> list = projectCoreService.findGroupList();
        return list;
    }

    @RequestMapping("project/verisons")
    @ResponseBody
    @Link(name = "项目-获取项目的版本列表",code = "admin-project-center-verisons",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public List<?> verisons(String id,String callback){
        List<Project> list = projectCoreService.findVersions(id);
        return list;
    }


    @RequestMapping("project/add")
    @ResponseBody
    @Link(name = "项目-新增",code = "admin-project-center-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String add(Project project,String callback){
        HttpResponse response = projectCoreService.addNewProject(project);
        return callback(callback,response);
    }

    @RequestMapping("project/edit/title")
    @ResponseBody
    @Link(name = "项目-修改项目的名称",code = "admin-project-center-edit-title",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String editProjectTitle(String id,String title,String callback){
        HttpResponse response = projectCoreService.editProjectTitle(id,title);
        return callback(callback,response);
    }

    @RequestMapping("project/edit/del")
    @ResponseBody
    @Link(name = "项目-标记为废弃",code = "admin-project-center-edit-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String editProjectToDel(String id,String deletecauses,String callback){
        HttpResponse response = projectCoreService.editProjectToDel(id,deletecauses);
        return callback(callback,response);
    }

    @RequestMapping("project/edit/his")
    @ResponseBody
    @Link(name = "项目-标记为历史",code = "admin-project-center-edit-his",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String editProjectToHis(String id,String iterationcauses,String callback){
        HttpResponse response = projectCoreService.editProjectToHis(id,iterationcauses);
        return callback(callback,response);
    }
}
