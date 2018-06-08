package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.service.ProModularService;
import com.ich.proman.project.service.ProPrototypeService;
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
public class ProModularController extends PromanController {

    @Autowired
    private ProPrototypeService prototypeService;
    @Autowired
    private ProModularService modularService;
    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/modular/center")
    @Link(name = "模块-模块中心",code = "admin-project-modular-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid,String modularid , String callback){
        String viewname = "project/modularNCenter";
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
//        Map<String,Object> modular = modularService.findModularDetailById(modularid);
//        model.put("modular",modular);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/modular/list")
    @ResponseBody
    @Link(name = "模块-项目模块列表",code = "admin-project-modular-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String modularList(ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<Map<String,Object>> result = this.projectCoreService.findModularList(query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/modular/add")
    @ResponseBody
    @Link(name = "模块-项目模块新增",code = "admin-project-modular-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String modularAdd(String projectid, String catalogid,String modularname, String callback){
        HttpResponse response = modularService.addModular(projectid,catalogid,modularname,false);
        return callback(callback,response);
    }

    @RequestMapping("project/modular/del")
    @ResponseBody
    @Link(name = "模块-项目模块删除",code = "admin-project-modular-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String modularDel(String id,String callback){
        HttpResponse response = modularService.deleteModular(id);
        return callback(callback,response);
    }
}
