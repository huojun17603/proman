package com.ich.proman.project.pojo;

/**
 * 项目模块
 * 模块只是一种分类行为，不存在版本问题
 */
public class ProModular {

    /** ID*/
    private String id;
    /** 项目ID */
    private String projectid;
    /** 目录*/
    private String catalogid;
    /** 模板名称 */
    private String modularname;
    /** 是否默认 */
    private Boolean isdefault;

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

    public String getModularname() {
        return modularname;
    }

    public void setModularname(String modularname) {
        this.modularname = modularname;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }
}
