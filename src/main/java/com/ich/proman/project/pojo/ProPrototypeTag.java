package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 项目原型标记
 * 新增：正常
 * 迭代：一条新的原型记录（理由），原有记录标记为历史
 * 被引入：允许原型复制一套同一分组的标记信息（全）
 * 移动坐标：正常
 */
public class ProPrototypeTag {

    private String id;

    private String prototypeid;

    private String mapx;

    private String mapy;
    /** 编号*/
    private String code;
    /** 长度2000以内 */
    private String content;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;
    /** 创建时间 */
    private Date createtime;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public String getPrototypeid() {
        return prototypeid;
    }

    public void setPrototypeid(String prototypeid) {
        this.prototypeid = prototypeid;
    }

    public String getMapx() {
        return mapx;
    }

    public void setMapx(String mapx) {
        this.mapx = mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public void setMapy(String mapy) {
        this.mapy = mapy;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
