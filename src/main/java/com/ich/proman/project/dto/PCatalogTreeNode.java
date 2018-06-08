package com.ich.proman.project.dto;

import com.ich.core.http.entity.EasyUITreeNode;

public class PCatalogTreeNode extends EasyUITreeNode {

    private String title;

    private String prototypeid;

    private String prototypename;

    private String remark;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrototypeid() {
        return prototypeid;
    }

    public void setPrototypeid(String prototypeid) {
        this.prototypeid = prototypeid;
    }

    public String getPrototypename() {
        return prototypename;
    }

    public void setPrototypename(String prototypename) {
        this.prototypename = prototypename;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
