package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProCatalog;
import com.ich.proman.project.pojo.ProPrototypeCatalog;
import com.ich.proman.project.service.ProCatalogService;
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
public class ProCatalogController extends PromanController {

    @Autowired
    private ProCatalogService catalogService;

    @RequestMapping("project/catalog/center")
    @Link(name = "项目目录-管理中心",code = "admin-project-catalog-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid , String callback){
        String viewname = "project/catalogCenter";
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/catalog/query")
    @ResponseBody
    @Link(name = "项目目录-列表",code = "admin-project-catalog-query",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String query(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.catalogService.findAllList(query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }


    @RequestMapping("project/catalog/add")
    @ResponseBody
    @Link(name = "项目目录-新增",code = "admin-project-catalog-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String add(ProCatalog catalog, String callback){
        HttpResponse response = catalogService.addCatalog(catalog);
        return callback(callback, response);
    }

    @RequestMapping("project/catalog/edit")
    @ResponseBody
    @Link(name = "项目目录-修改",code = "admin-project-catalog-edit",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String edit(ProCatalog catalog, String callback){
        HttpResponse response = catalogService.editCatalog(catalog);
        return callback(callback, response);
    }

    @RequestMapping("project/catalog/copy")
    @ResponseBody
    @Link(name = "项目目录-复制",code = "admin-project-catalog-copy",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String copy(String id,String title, String callback){
        HttpResponse response = catalogService.addCopyCatalog(id,title);
        return callback(callback, response);
    }

}
