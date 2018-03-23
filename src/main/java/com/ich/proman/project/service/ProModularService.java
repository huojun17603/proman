package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProRole;

import java.util.Map;

public interface ProModularService {

    /**
     * 新增：正常
     */
    public HttpResponse addModular(String projectid,String modularname,Boolean isdefault);

    /**
     * 修改：仅可以修改模块名称
     */
    public HttpResponse editModularName(String id,String modularname);

    /**
     * 删除：不允许删除默认模块，不允许删除有内容的模块
     */
    public HttpResponse deleteModular(String id);

    Map<String,Object> findModularDetailById(String modularid);
}
