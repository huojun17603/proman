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
}
