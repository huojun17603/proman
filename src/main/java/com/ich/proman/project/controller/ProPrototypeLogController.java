package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.PageView;
import com.ich.module.annotation.Link;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.service.ProPrototypeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ProPrototypeLogController extends PromanController {

    @Autowired
    private ProPrototypeLogService prototypeLogService;

    @RequestMapping("project/prototype/log/list")
    @ResponseBody
    @Link(name = "原型日志-列表",code = "admin-project-prototype-log-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String query(PageView view , ProjectQuery query, String callback){
        Map<String,Object> model = getSuccessMap();
        List<?> result = this.prototypeLogService.findListByQuery(view,query);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, view.getRowCount());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }
}
