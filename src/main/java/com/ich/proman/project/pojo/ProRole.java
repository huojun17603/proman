package com.ich.proman.project.pojo;

/***
 * 项目角色人员
 * 注：现目前没有权限一说
 */
public class ProRole {

    //提供固定的角色
    /** 项目经理 */
    public static Integer PROJECT_MANAGER = 1;
    /** 原型设计 */
    public static Integer PROJECT_PROTOTYPE_MANAGER = 1;
    /** 美工 */
    public static Integer PROJECT_ART_MANAGER = 1;
    /** 开发人员 */
    public static Integer PROJECT_DEVELOP_MANAGER = 1;

    public static String FINDROLENAME(Integer role) {
        return  "";
    }

    /** 项目角色人员ID */
    private String id;
    /** 所属项目ID */
    private String projectid;
    /** 人员ID */
    private String userid;
    /** 人员名称 */
    private String username;
    /** 所属角色 */
    private Integer role;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }


}
