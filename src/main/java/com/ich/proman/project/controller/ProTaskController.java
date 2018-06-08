package com.ich.proman.project.controller;

import com.ich.admin.dto.LocalEmployee;
import com.ich.core.base.TimeUtil;
import com.ich.core.file.ExcelUtil;
import com.ich.core.file.FileUtil;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProTaskEXLSService;
import com.ich.proman.project.service.ProTaskService;
import com.ich.proman.project.service.ProjectCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class ProTaskController extends PromanController {

    @Autowired
    private ProTaskService taskService;
    @Autowired
    private ProTaskEXLSService taskEXLSService;
    @Autowired
    private ProjectCoreService projectCoreService;

    @RequestMapping("project/task/center")
    @Link(name = "任务-管理中心",code = "admin-project-task-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid, String callback){
        String viewname = "project/taskCenter";
        Project project = projectCoreService.findById(projectid);
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        model.put("project",project);
        return new ModelAndView(viewname,model);
    }

    @RequestMapping("project/task/list")
    @ResponseBody
    @Link(name = "任务-列表",code = "admin-project-task-list",
            level = Link.LEVEL_NONE,parent = "admin-project-center-index")
    public String list(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> messages  = taskService.findListByQuery(view,query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, view.getRowCount());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, messages);
        return callback(callback, model);
    }

    @RequestMapping("project/task/add")
    @ResponseBody
    @Link(name = "任务-新增",code = "admin-project-task-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String taskAdd(ProTask task, String callback){
        HttpResponse response = taskService.addTask(task);
        return callback(callback,response);
    }

    @RequestMapping("project/task/edit")
    @ResponseBody
    @Link(name = "任务-修改",code = "admin-project-task-edit",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String taskEdit(ProTask task, String callback){
        HttpResponse response = taskService.editTask(task);
        return callback(callback,response);
    }

    @RequestMapping("project/task/del")
    @ResponseBody
    @Link(name = "任务-删除",code = "admin-project-task-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String del(String id, String callback){
        HttpResponse response = taskService.delTask(id);
        return callback(callback,response);
    }

    @RequestMapping("project/task/edit/appoint")
    @ResponseBody
    @Link(name = "任务-指派任务",code = "admin-project-task-edit-appoint",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String appoint(String id, Date estimatetime, String userid, String username, String callback){
        HttpResponse response = taskService.editTaskToAppoint(id,estimatetime,userid,username);
        return callback(callback,response);
    }

    @RequestMapping("project/task/edit/receive")
    @ResponseBody
    @Link(name = "任务-领取任务",code = "admin-project-task-edit-receive",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String receive(String id,Date estimatetime, String callback){
        HttpResponse response = taskService.editTaskToReceive(id,estimatetime);
        return callback(callback,response);
    }

    @RequestMapping("project/task/edit/complete")
    @ResponseBody
    @Link(name = "任务-完成任务",code = "admin-project-task-edit-complete",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String complete(String id, String callback){
        HttpResponse response = taskService.editTaskToComplete(id);
        return callback(callback,response);
    }


    @RequestMapping("project/task/file/upload")
    @ResponseBody
    @Link(name = "任务-上传",code = "admin-project-task-file-upload",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String upload(String projectid,HttpServletRequest request, HttpServletResponse response, String callback){
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        HttpResponse result = new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile multipartFile = entity.getValue();
            result = taskEXLSService.executeUpload(projectid,multipartFile);
        }
        return callback(callback,result);
    }

    @RequestMapping("project/task/file/down")
    @ResponseBody
    @Link(name = "任务-下传",code = "admin-project-task-file-down",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public void upload(String projectid,String catalogids,String terminals, HttpServletResponse response, String callback){
        taskEXLSService.executedown(projectid,catalogids,terminals,response);
    }

}
