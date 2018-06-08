package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 项目原型
 * 新增：
 * 迭代：一条新的原型记录（理由），原有记录标记为历史，使用全新的标记信息
 * 修改：仅可以修改标题
 * 删除：允许创建者标记版本为删除状态，但必须说明删除原因（目的：仅保留有价值的版本给团队浏览）
 */
public class ProPrototype {

    private String id;
    /** 项目ID*/
    private String projectid;
    /** 模块ID */
    private String catalogid;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;
    /** 创建时间 */
    private Date createtime;
    /** */
    private Integer isdefault;
    /** 标题 */
    private String title;
    /** 图片的修改，只允许是版本替换的方式 */
    private String img;
    /** 分组ID，如果是新建则同本记录ID一致，如果是替代则和被替代记录的分组ID一致 */
    private String groupid;
    /** 分组状态，1：当前，2：历史，3：删除 */
    private Integer groupstatus;
    /** 替换理由：由新版本记录 */
    private String iterationcauses;
    /** 删除理由 */
    private String deletecauses;

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

    public Integer getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Integer isdefault) {
        this.isdefault = isdefault;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
