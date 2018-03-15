package com.ich.proman.project.pojo;

import java.util.Date;

/***
 * 项目
 */
public class Project {

    /** ID */
    private String id;
    /** 项目名称 */
    private String title;
    /** 项目状态 1正常；2历史；3废弃*/
    private Integer status;
    /** 立项时间：迭代时保留原有的立项时间不变 */
    private Date createtime;
    /** 迭代时间：立项时与立项时间一致 */
    private Date iterationtime;
    /** 立项/迭代操作者ID */
    private String userid;
    /** 立项/迭代操作者名称 */
    private String username;
    /** 版本ID，如果是新建则同本记录ID一致，如果是迭代则和被迭代记录的版本ID一致 */
    private String versionid;
    /** 项目版本：用于项目的迭代，新的迭代会复制原有版本的所有数据，原有版本会被只读保留 */
    private String version;
    /** 删除理由 */
    private String deletecauses;
    /** 替换理由：由新版本记录 */
    private String iterationcauses;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getIterationtime() {
        return iterationtime;
    }

    public void setIterationtime(Date iterationtime) {
        this.iterationtime = iterationtime;
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

    public String getVersionid() {
        return versionid;
    }

    public void setVersionid(String versionid) {
        this.versionid = versionid;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeletecauses() {
        return deletecauses;
    }

    public void setDeletecauses(String deletecauses) {
        this.deletecauses = deletecauses;
    }

    public String getIterationcauses() {
        return iterationcauses;
    }

    public void setIterationcauses(String iterationcauses) {
        this.iterationcauses = iterationcauses;
    }
}
