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
    private String modularid;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;
    /** 创建时间 */
    private Date createtime;
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
}
