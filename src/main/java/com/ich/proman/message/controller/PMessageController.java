package com.ich.proman.message.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.pojo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class PMessageController extends PromanController {

    @Autowired
    private PMessageService messageService;

    @RequestMapping("message/unreadnum")
    @ResponseBody
    @Link(name = "项目消息-未读消息数量",code = "admin-message-unreadnum",
            level = Link.LEVEL_NONE,parent = "ICH-ADMIN")
    public String unreadnum(String callback){
        HttpResponse response = messageService.findUnreadNum();
        return callback(callback,response);
    }

    @RequestMapping("message/unreadlist")
    @ResponseBody
    @Link(name = "项目消息-未读消息列表",code = "admin-message-unreadlist",
            level = Link.LEVEL_NONE,parent = "ICH-ADMIN")
    public String unreadlist(String callback){
        Map<String,Object> model = getSuccessMap();
        List<PMessage> messages  = messageService.findUnreadList();
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, messages.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, messages);
        return callback(callback, model);
    }
}
