package com.ich.proman.message.pojo;

import java.util.Date;

public class PTheme {

    private String id;
    //房间标题
    private String title;

    private String projectid;

    private String modularid;

    private Integer source;

    private String sourceid;

    private String sourceremark;

    private Date createtime;

    private String userid;

    private String username;

    private Integer status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }

    public String getSourceremark() {
        return sourceremark;
    }

    public void setSourceremark(String sourceremark) {
        this.sourceremark = sourceremark;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
