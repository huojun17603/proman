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
    public static Integer PROJECT_PROTOTYPE_MANAGER = 2;
    /** 美工 */
    public static Integer PROJECT_ART_MANAGER = 3;
    /** JAVA开发 */
    public static Integer PROJECT_JAVA_MANAGER = 4;
    /** IOS开发 */
    public static Integer PROJECT_IOS_MANAGER = 5;
    /** 安卓开发 */
    public static Integer PROJECT_ANDRIOD_MANAGER = 6;
    /** 测试 */
    public static Integer PROJECT_TEST = 7;

    public static String FINDROLENAME(Integer role) {
        if(PROJECT_MANAGER.equals(role)){
            return  "项目经理";
        }
        if(PROJECT_PROTOTYPE_MANAGER.equals(role)){
            return  "原型设计";
        }
        if(PROJECT_ART_MANAGER.equals(role)){
            return  "美工";
        }
        if(PROJECT_JAVA_MANAGER.equals(role)){
            return  "JAVA开发";
        }
        if(PROJECT_IOS_MANAGER.equals(role)){
            return  "IOS开发";
        }
        if(PROJECT_ANDRIOD_MANAGER.equals(role)){
            return  "安卓开发";
        }
        if(PROJECT_TEST.equals(role)){
            return  "测试";
        }
        return "";
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
