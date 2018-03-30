package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeTag;
import com.ich.proman.project.service.ProPrototypeService;
import com.ich.proman.project.service.ProPrototypeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ProPrototypeTagController extends PromanController {

    @Autowired
    private ProPrototypeTagService prototypeTagService;

    @RequestMapping("project/prototype/tag/detail")
    @ResponseBody
    @Link(name = "原型标记-列表",code = "admin-project-prototype-tag-detail",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String detail(String id,String callback){
        HttpResponse response =  this.prototypeTagService.findById(id);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/tag/list")
    @ResponseBody
    @Link(name = "原型标记-列表",code = "admin-project-prototype-tag-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String tagList(String pid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.prototypeTagService.findNormalListByPid(pid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/prototype/tag/hislist")
    @ResponseBody
    @Link(name = "原型标记-历史版本列表",code = "admin-project-prototype-tag-hislist",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String tagHisList(String id,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.prototypeTagService.findVersions(id);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }


    @RequestMapping("project/prototype/tag/add")
    @ResponseBody
    @Link(name = "原型标记-新增",code = "admin-project-prototype-tag-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String tagAdd(ProPrototypeTag tag,String callback){
        HttpResponse response = this.prototypeTagService.addTag(tag);
        return callback(callback, response);
    }


    @RequestMapping("project/prototype/tag/iteration")
    @ResponseBody
    @Link(name = "原型标记-迭代",code = "admin-project-prototype-tag-iteration",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String iteration(String id,String content,String iterationcauses,String callback){
        HttpResponse response = this.prototypeTagService.editIteration(id,content,iterationcauses);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/tag/map")
    @ResponseBody
    @Link(name = "原型标记-移动",code = "admin-project-prototype-tag-map",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String map(String id,String mapx,String mapy,String callback){
        HttpResponse response = this.prototypeTagService.editMap(id,mapx,mapy);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/tag/imports")
    @ResponseBody
    @Link(name = "原型标记-引入",code = "admin-project-prototype-tag-imports",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String imports(String ids,String prototypeid,String callback){
        HttpResponse response = this.prototypeTagService.addTagByImports(prototypeid,ids);
        return callback(callback, response);
    }
}
