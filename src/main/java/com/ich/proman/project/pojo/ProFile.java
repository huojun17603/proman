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


}
