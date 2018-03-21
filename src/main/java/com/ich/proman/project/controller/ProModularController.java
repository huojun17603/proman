package com.ich.proman.project.controller;

import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ProModularController extends PromanController {

    @RequestMapping("modular/center")
    @Link(name = "模块-模块中心",code = "admin-project-modular-center",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public ModelAndView center(String projectid,String modularid , String callback){
        String viewname = "project/modularCenter";
        Map<String,Object> model = new HashMap<>();
        model.put("projectid",projectid);
        return new ModelAndView(viewname,model);
    }
}
