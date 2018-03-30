package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 测试用例
 */
public class ProTest {

    private String id;

    private String code;
    /** 项目ID*/
    private String projectid;
    /** 模块ID */
    private String modularid;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;
    /** 创建时间 */
    private Date createtime;
    /** 标题 */
    private String title;
    /** 长度2000以内 */
    private String content;
    /** 1:正常；2：废弃*/
    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProjectid() {
        return projectid;
    }

    public void setProjectid(String projectid) {
        this.projectid = projectid;
    }

    public String getModularid() {
        return modularid;
    }

    public void setModularid(String modularid) {
        this.modularid = modularid;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
