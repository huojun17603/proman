package com.ich.proman.project.controller;

import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProBug;
import com.ich.proman.project.pojo.ProTest;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProBugService;
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
public class ProBugController extends PromanController {

    @Autowired
    ProBugService bugService;
    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/bug/center")
    @Link(name = "BUG-管理中心",code = "admin-project-bug-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid, String callback){
        String viewname = "project/bugCenter";
        Project project = projectCoreService.findById(projectid);
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        model.put("project",project);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/bug/list")
    @ResponseBody
    @Link(name = "BUG-列表",code = "admin-project-bug-list",
            level = Link.LEVEL_NONE,parent = "admin-project-center-index")
    public String list(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> messages  = bugService.findListByQuery(view,query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, view.getRowCount());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, messages);
        return callback(callback, model);
    }

    @RequestMapping("project/bug/detail")
    @ResponseBody
    @Link(name = "BUG-明细",code = "admin-project-bug-detail",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String detail(String id, String callback){
        Map<String,Object> model = getSuccessMap();
        ProBug bug = bugService.findById(id);
        model.put(HttpResponse.RETURN_DATA,bug);
        return callback(callback,model);
    }

    @RequestMapping("project/bug/add")
    @ResponseBody
    @Link(name = "BUG-新增",code = "admin-project-bug-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String add(ProBug bug, String callback){
        HttpResponse response = bugService.addBUG(bug);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/open")
    @ResponseBody
    @Link(name = "BUG-开启",code = "admin-project-bug-open",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String open(ProBug bug, String callback){
        HttpResponse response = bugService.editToOpen(bug);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/test")
    @ResponseBody
    @Link(name = "BUG-测试",code = "admin-project-bug-test",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String test(String id,String repairremark, String callback){
        HttpResponse response = bugService.editToTest(id,repairremark);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/complete")
    @ResponseBody
    @Link(name = "BUG-完成",code = "admin-project-bug-complete",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String complete(String id, String callback){
        HttpResponse response = bugService.editToCom(id);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/reopen")
    @ResponseBody
    @Link(name = "BUG-重新开启",code = "admin-project-bug-reopen",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String reopen(String id, String callback){
        HttpResponse response = bugService.editToReopen(id);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/close")
    @ResponseBody
    @Link(name = "BUG-关闭",code = "admin-project-bug-close",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String close(String id,String repairremark, String callback){
        HttpResponse response = bugService.editToColse(id,repairremark);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/appoint")
    @ResponseBody
    @Link(name = "BUG-指派BUG",code = "admin-project-bug-appoint",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String appoint(String id,String userid,String username, String callback){
        HttpResponse response = bugService.editBUGToAppoint(id,userid,username);
        return callback(callback,response);
    }

    @RequestMapping("project/bug/receive")
    @ResponseBody
    @Link(name = "BUG-领取BUG",code = "admin-project-bug-receive",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String receive(String id, String callback){
        HttpResponse response = bugService.editBUGToReceive(id);
        return callback(callback,response);
    }

}
