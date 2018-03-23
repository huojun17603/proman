package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 项目文件
 * 新增、替换：替换时必须保证“.”前的名称一致
 * 修改：不存在的行为
 * 删除：允许创建者标记历史版本为删除状态，但必须说明删除原因（目的：仅保留有价值的版本给团队浏览）
 */
public class ProFile {
    //项目、模块、原型、测试、设计

    private String id;

    private String projectid;

    private String modularid;

    private String source;

    private String sourceid;

    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;

    private String name;
    /** 文件后缀；主要用于区别是否可以在页面打开 */
    private String suffix;

    private String filename;

    private String file;

    /** 分组ID，如果是新建则同本记录ID一致，如果是替代则和被替代记录的分组ID一致 */
    private String groupid;
    /** 分组状态，1：当前，2：历史，3：删除 */
    private Integer groupstatus;
    /** 替换理由：由新版本记录 */
    private String iterationcauses;
    /** 删除理由 */
    private String deletecauses;
    /** 创建时间 */
    private Date createtime;

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

    public String getModularid() {
        return modularid;
    }

    public void setModularid(String modularid) {
        this.modularid = modularid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public Integer getGroupstatus() {
        return groupstatus;
    }

    public void setGroupstatus(Integer groupstatus) {
        this.groupstatus = groupstatus;
    }

    public String getIterationcauses() {
        return iterationcauses;
    }

    public void setIterationcauses(String iterationcauses) {
        this.iterationcauses = iterationcauses;
    }

    public String getDeletecauses() {
        return deletecauses;
    }

    public void setDeletecauses(String deletecauses) {
        this.deletecauses = deletecauses;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
