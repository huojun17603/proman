package com.ich.proman.base;

public class ProjectQuery {

    private String searchkey;

    private String projectid;

    private String modularid;

    private String userid;

    private Integer classes;

    private Integer status;

    private String btime;

    private String etime;

    public String getSearchkey() {
        return searchkey;
    }

    public void setSearchkey(String searchkey) {
        this.searchkey = searchkey;
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

    public Integer getClasses() {
        return classes;
    }

    public void setClasses(Integer classes) {
        this.classes = classes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBtime() {
        return btime;
    }

    public void setBtime(String btime) {
        this.btime = btime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
