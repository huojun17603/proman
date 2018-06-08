package com.ich.proman.rtc.controller;

import com.ich.admin.controller.AdminController;
import com.ich.admin.dto.EmployeeMenuDto;
import com.ich.admin.dto.EmployeeWindowDto;
import com.ich.admin.dto.LocalEmployee;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("rtc")
public class RTCController  extends PromanController {

    @RequestMapping("rtc")
    @Link(name = "首页",code = "admin-rtc", level = Link.LEVEL_NONE,parent = "ICH-ADMIN")
    public ModelAndView index(HttpServletRequest request){
        return new ModelAndView("rtc/rtc");
    }
}
