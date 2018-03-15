package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProRole;

import java.util.List;

public interface ProRoleService {

    /**
     * 新增：在项目正常时都是被允许的操作
     */
    public HttpResponse addRole(ProRole role);

    /**
     * 删除：在项目正常时都是被允许的操作
     */
    public HttpResponse deleteRole(String id);

    /** 获取此项目下的所有角色 */
    public List<ProRole> findProRole(String projectid);

}
