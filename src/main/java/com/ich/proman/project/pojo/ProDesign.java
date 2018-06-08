package com.ich.proman.project.pojo;

import java.util.Date;

public class ProDesign {
    //项目、模块、原型、测试、设计
    private String id;

    private String projectid;

    private String modularid;

    private String source;

    private String sourceid;
    /** 来源说明*/
    private String sourceremark;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;

    /** 文件后缀；主要用于区别是否可以在页面打开 */
    private String suffix;
    /** 文件原名称*/
    private String filename;
    /** 文件路径 */
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
}
