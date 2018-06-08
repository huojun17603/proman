package com.ich.proman.project.controller;

import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.ProTest;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProTestService;
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
public class ProTestController extends PromanController {

    @Autowired
    private ProTestService testService;
    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/test/center")
    @Link(name = "用例-管理中心",code = "admin-project-test-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid, String callback){
        String viewname = "project/testCenter";
        Project project = projectCoreService.findById(projectid);
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        model.put("project",project);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/test/list")
    @ResponseBody
    @Link(name = "用例-列表",code = "admin-project-test-list",
            level = Link.LEVEL_NONE,parent = "admin-project-center-index")
    public String list(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> messages  = testService.findListByQuery(view,query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, view.getRowCount());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, messages);
        return callback(callback, model);
    }

    @RequestMapping("project/test/add")
    @ResponseBody
    @Link(name = "用例-新增",code = "admin-project-test-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String add(ProTest test, String callback){
        HttpResponse response = testService.addTest(test);
        return callback(callback,response);
    }

    @RequestMapping("project/test/edit")
    @ResponseBody
    @Link(name = "用例-修改",code = "admin-project-test-edit",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String edit(ProTest test, String callback){
        HttpResponse response = testService.editTest(test);
        return callback(callback,response);
    }

    @RequestMapping("project/test/del")
    @ResponseBody
    @Link(name = "用例-废弃",code = "admin-project-test-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String del(String id, String callback){
        HttpResponse response = testService.delTest(id);
        return callback(callback,response);
    }

    @RequestMapping("project/test/detail")
    @ResponseBody
    @Link(name = "用例-明细",code = "admin-project-test-detail",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String detail(String id, String callback){
        Map<String,Object> model = getSuccessMap();
        ProTest test = testService.findById(id);
        model.put(HttpResponse.RETURN_DATA,test);
        return callback(callback,model);
    }


}
