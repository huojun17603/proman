package com.ich.proman.project.controller;

import com.ich.core.base.JsonUtils;
import com.ich.core.http.entity.HttpEasyUIResponse;
import com.ich.core.http.entity.HttpResponse;
import com.ich.module.annotation.Link;
import com.ich.proman.base.PromanController;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class ProRoleController extends PromanController {

    @Autowired
    private ProRoleService roleService;

    @RequestMapping("project/role/list")
    @ResponseBody
    @Link(name = "角色-项目角色列表",code = "admin-project-role-list",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String roleList(String projectid,String callback){
        Map<String,Object> model = getSuccessMap();
        List<ProRole> result = this.roleService.findProRole(projectid);
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_TOTAL, result.size());
        model.put(HttpEasyUIResponse.HTTP_DATA_PAGE_ROWS, result);
        return callback(callback, JsonUtils.objectToJson(model));
    }

    @RequestMapping("project/role/add")
    @ResponseBody
    @Link(name = "角色-项目角色列表",code = "admin-project-role-add",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String roleList(ProRole role, String callback){
        HttpResponse response = roleService.addRole(role);
        return callback(callback,response);
    }

    @RequestMapping("project/role/del")
    @ResponseBody
    @Link(name = "角色-项目角色列表",code = "admin-project-role-del",parent = "admin-project-center-index", level = Link.LEVEL_NONE)
    public String roleDel(String id,String callback){
        HttpResponse response = roleService.deleteRole(id);
        return callback(callback,response);
    }
}
