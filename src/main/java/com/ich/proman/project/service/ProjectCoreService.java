package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.Project;

import java.util.List;
import java.util.Map;

public interface ProjectCoreService {

    /**
     * 新增
     * 用户可以增加一个新的项目，要求填写项目的基本信息。新的项目会自带首页，菜单，杂项三个模块；
     * @param project 项目信息
     * @return
     */
    public HttpResponse addNewProject(Project project);

    /**
     * 修改：仅允许修改项目的名称，所有版本一起修改
     */
    public HttpResponse editProjectTitle(String id,String title);

    /**
     * 废弃：填写废弃理由，并标记为废弃，不再允许其下再有任何非查询操作
     */
    public HttpResponse editProjectToDel(String id,String deletecauses);

    /**
     * 历史：填写标记理由，并标记为历史，不再允许其下再有任何非查询操作
     */
    public HttpResponse editProjectToHis(String id,String iterationcauses);

    /**
     * 版本分离
     * 用于：
     * 1.把一个基础项目分割为多个项目，然后独立开发其不同的版本
     * 2.把一个项目版本完整的保留下来
     * 方案：拷贝项目下所有信息，除人员信息
     */
    public HttpResponse exeversion(String id,String version);

    /**
     * 验证当前项目是否可以操作
     */
    public boolean effective(String id);
    /** 获取项目分组列表 */
    List<Project> findGroupList();

    List<Project> findVersions(String id);

    Project findById(String id);

    List<Map<String,Object>>  findModularListByPid(String id);
}
