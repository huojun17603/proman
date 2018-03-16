package com.ich.proman.message.controller;

import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.pojo.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
