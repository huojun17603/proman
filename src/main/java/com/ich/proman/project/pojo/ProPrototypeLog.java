package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 原型修改日志
 * */
public class ProPrototypeLog {

    private String id;

    private Integer logclass;

    private String catalogid;

    private String prototypeid;

    private String prototypetitle;

    private String prototypetagid;

    private Date createtime;

    private String userid;

    private String username;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLogclass() {
        return logclass;
    }

    public void setLogclass(Integer logclass) {
        this.logclass = logclass;
    }

    public String getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(String catalogid) {
        this.catalogid = catalogid;
    }

    public String getPrototypeid() {
        return prototypeid;
    }

    public void setPrototypeid(String prototypeid) {
        this.prototypeid = prototypeid;
    }

    public String getPrototypetitle() {
        return prototypetitle;
    }

    public void setPrototypetitle(String prototypetitle) {
        this.prototypetitle = prototypetitle;
    }

    public String getPrototypetagid() {
        return prototypetagid;
    }

    public void setPrototypetagid(String prototypetagid) {
        this.prototypetagid = prototypetagid;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
