package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProFile;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProFileService;
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
public class ProFileController extends PromanController {

    @Autowired
    private ProFileService fileService;
    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/file/center")
    @Link(name = "文件-管理中心",code = "admin-project-file-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid, String callback){
        String viewname = "project/fileCenter";
        Project project = projectCoreService.findById(projectid);
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        model.put("project",project);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/file/add")
    @ResponseBody
    @Link(name = "文件-新增",code = "admin-project-file-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String fileAdd(ProFile file, String callback){
        HttpResponse response = fileService.addFile(file);
        return callback(callback, response);
    }

    @RequestMapping("project/file/edit")
    @ResponseBody
    @Link(name = "文件-迭代",code = "admin-project-file-edit",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String fileEdit(String id,String file,String iterationcauses, String callback){
        HttpResponse response = fileService.editFile(id,file,iterationcauses);
        return callback(callback, response);
    }

    @RequestMapping("project/file/del")
    @ResponseBody
    @Link(name = "文件-删除",code = "admin-project-file-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String fileDel(String id,String deletecauses, String callback){
        HttpResponse response = fileService.delFile(id,deletecauses);
        return callback(callback, response);
    }


    @RequestMapping("project/file/listbyp")
    @ResponseBody
    @Link(name = "文件-列表（项目）",code = "admin-project-file-listbyp",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String listbyp(String projectid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.fileService.findNormalListByPid(projectid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/countbyp")
    @ResponseBody
    @Link(name = "文件-总数（项目）",code = "admin-project-file-countbyp",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String countbyp(String projectid,String callback){
        Map<String,Object> model = getSuccessMap();
        Integer count = this.fileService.findCountNormalListByPid(projectid);
        model.put("count",count);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/listbym")
    @ResponseBody
    @Link(name = "文件-列表（模块）",code = "admin-project-file-listbym",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String listbym(String modularid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.fileService.findNormalListByMid(modularid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/countbym")
    @ResponseBody
    @Link(name = "文件-总数（模块）",code = "admin-project-file-countbym",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String countbym(String modularid,String callback){
        Map<String,Object> model = getSuccessMap();
        Integer count = this.fileService.findCountNormalListByMid(modularid);
        model.put("count",count);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/listbys")
    @ResponseBody
    @Link(name = "文件-列表（来源）",code = "admin-project-file-listbys",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String listbys(String projectid,String source,String sourceid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.fileService.findNormalListBySource(projectid,source,sourceid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/countbys")
    @ResponseBody
    @Link(name = "文件-总数（来源）",code = "admin-project-file-countbys",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String countbys(String projectid,String source,String sourceid,String callback){
        Map<String,Object> model = getSuccessMap();
        Integer count = this.fileService.findCountNormalListBySource(projectid,source,sourceid);
        model.put("count",count);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/file/hislist")
    @ResponseBody
    @Link(name = "原型标记-历史版本列表",code = "admin-project-file-hislist",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String hislist(String id,String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.fileService.findVersions(id);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

}
