package com.ich.proman.message.controller;

import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.LocalEmployeeService;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.service.ProTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class MyTaskController extends PromanController {

    @Autowired
    private ProTaskService taskService;
    @Autowired
    private LocalEmployeeService localEmployeeService;

    @RequestMapping("project/task/mlist")
    @ResponseBody
    @Link(name = "用户中心-我的任务",code = "admin-center-task-mlist",
            level = Link.LEVEL_NONE,parent = "admin-center-task-index")
    public String mlist(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        LocalEmployee employee = localEmployeeService.findLocalEmployee();
        query.setUserid(employee.getEmployeeId());
        List<?> messages  = taskService.findListByQuery(view,query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, view.getRowCount());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, messages);
        return callback(callback, model);
    }

}
