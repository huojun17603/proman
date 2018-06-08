package com.ich.proman.project.pojo;

import java.util.Date;

public class ProTask {

    private String id;

    private String code;
    /** 项目ID*/
    private String projectid;
    /** 目录ID */
    private String catalogid;
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
    /** 领取时间 */
    private Date receivetime;
    /** 完成时间 */
    private Date completetime;
    /** 领取人ID */
    private String receiveid;
    /** 领取人 */
    private String receivename;
    /** 1：代领；2：已领；3：完成；4：删除 */
    private Integer status;

    private String terminal;
    /** 工时：按小时（一天按8小时间算）*/
    private Integer power;
    /** 完成实际花费工时：按小时（一天按8小时间算）*/
    private Integer compower;
    /** 领取时填写:预计完成时间 */
    private Date estimatetime;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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

    public String getCatalogid() {
        return catalogid;
    }

    public void setCatalogid(String catalogid) {
        this.catalogid = catalogid;
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

    public Date getReceivetime() {
        return receivetime;
    }

    public void setReceivetime(Date receivetime) {
        this.receivetime = receivetime;
    }

    public Date getCompletetime() {
        return completetime;
    }

    public void setCompletetime(Date completetime) {
        this.completetime = completetime;
    }

    public String getReceiveid() {
        return receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }

    public String getReceivename() {
        return receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Date getEstimatetime() {
        return estimatetime;
    }

    public void setEstimatetime(Date estimatetime) {
        this.estimatetime = estimatetime;
    }

    public Integer getCompower() {
        return compower;
    }

    public void setCompower(Integer compower) {
        this.compower = compower;
    }
}
