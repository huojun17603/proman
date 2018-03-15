package com.ich.proman.admin.controller;

import com.ich.admin.controller.AdminController;
import com.ich.admin.service.AdminLoginService;
import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.base.PromanController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController extends PromanController {

    @Autowired
    private AdminLoginService adminLoginService;

    @RequestMapping("/")
    public ModelAndView loginView(HttpServletRequest request){
        return new ModelAndView("index");
    }

    @RequestMapping(value="login",produces= MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String login(HttpServletRequest request,String logincode,String loginkey,String callback){
        String sessionid = request.getSession().getId();
        HttpResponse response = this.adminLoginService.executeLogin(sessionid,logincode,loginkey);
        if(response.getStatus() == HttpResponse.HTTP_OK){
            request.getSession().setAttribute(AdminController.SESSION_USERID, response.getData());
            return callback(callback, JsonUtils.objectToJson(getSuccessMap(response)));
        }
        return callback(callback, JsonUtils.objectToJson(response));
    }

    @RequestMapping("out")
    public ModelAndView loginOut(HttpServletRequest request){
        request.getSession().invalidate();
        return new ModelAndView("index");
    }
}
