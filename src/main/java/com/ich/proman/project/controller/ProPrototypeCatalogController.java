package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeCatalog;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProPrototypeCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProPrototypeCatalogController extends PromanController {

    @Autowired
    private ProPrototypeCatalogService prototypeCatalogService;

    @RequestMapping("project/pcatalog/center")
    @Link(name = "原型-原型管理中心",code = "admin-project-pcatalog-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid , String callback){
        String viewname = "project/pcatalogCenter";
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/pcatalog/list")
    @ResponseBody
    @Link(name = "原型目录-列表",code = "admin-project-pcatalog-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String query(String catalogid ,String fid, String callback){
        List<?> result = this.prototypeCatalogService.findListByFid(catalogid,fid);
        return callback(callback, JsonUtils.objectToJson(result));
    }

    @RequestMapping("project/pcatalog/tree")
    @ResponseBody
    @Link(name = "原型目录-树",code = "admin-project-pcatalog-tree",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String tree(String catalogid ,String fid, String callback){
        List<?> result = this.prototypeCatalogService.findTree(catalogid,null);
        return callback(callback, JsonUtils.objectToJson(result));
    }

    @RequestMapping("project/pcatalog/add")
    @ResponseBody
    @Link(name = "原型目录-新增",code = "admin-project-pcatalog-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String add(ProPrototypeCatalog pcatalog, String callback){
        HttpResponse response = prototypeCatalogService.addPCatalog(pcatalog);
        return callback(callback, response);
    }

    @RequestMapping("project/pcatalog/edit")
    @ResponseBody
    @Link(name = "原型目录-修改",code = "admin-project-pcatalog-edit",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String edit(ProPrototypeCatalog pcatalog, String callback){
        HttpResponse response = prototypeCatalogService.editPCatalog(pcatalog);
        return callback(callback, response);
    }

    @RequestMapping("project/pcatalog/del")
    @ResponseBody
    @Link(name = "原型目录-删除",code = "admin-project-pcatalog-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String del(String id, String callback){
        HttpResponse response = prototypeCatalogService.delPCatalog(id);
        return callback(callback, response);
    }

}
