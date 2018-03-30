package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.service.ProPrototypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ProPrototypeController  extends PromanController {

    @Autowired
    private ProPrototypeService prototypeService;

    @RequestMapping("project/prototype/list")
    @ResponseBody
    @Link(name = "原型-项目原型列表",code = "admin-project-prototype-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeList(String modularid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.prototypeService.findNormalListByMid(modularid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/prototype/add")
    @ResponseBody
    @Link(name = "原型-项目原型新增",code = "admin-project-prototype-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeAdd(ProPrototype prototype, String callback){
        HttpResponse response = prototypeService.addPrototype(prototype);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/edit/title")
    @ResponseBody
    @Link(name = "原型-项目原型修改标题",code = "admin-project-prototype-edit-title",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeEditTitle(String id,String title,String callback){
        HttpResponse response = prototypeService.editPrototypeTitle(id,title);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/edit/dis")
    @ResponseBody
    @Link(name = "原型-项目原型标记为废弃",code = "admin-project-prototype-edit-dis",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeEditDis(String id,String deletecauses,String callback){
        HttpResponse response = prototypeService.editPrototypeToDel(id,deletecauses);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/edit/img")
    @ResponseBody
    @Link(name = "原型-项目原型迭代",code = "admin-project-prototype-edit-img",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeEditImg(String id,String img,String iterationcauses,Boolean imports, String callback){
        if(ObjectHelper.isEmpty(imports)) imports = false;
        HttpResponse response = prototypeService.editPrototypeToImg(id,img,iterationcauses,imports);
        return callback(callback, response);
    }

    @RequestMapping("project/prototype/hislist")
    @ResponseBody
    @Link(name = "原型-项目原型历史版本列表",code = "admin-project-prototype-hislist",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String prototypeHisList(String id,String callback){
        Map<String,Object> model = getSuccessMap();
        List<ProPrototype> result = prototypeService.findVersions(id);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

}
