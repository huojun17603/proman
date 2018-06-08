package com.ich.proman.project.pojo;

import java.util.Date;

/**
 * 项目BUG
 * 新增过程：
 * 1：从用例来：从测试用例直接生产BUG
 * 2：直接发现，填写：重现步骤、实际结果、预期结果；与现有用例绑定
 */
public class ProBug {

    private String id;
    /** 项目ID*/
    private String projectid;
    /** 模块ID */
    private String modularid;
    /** 目录ID */
    private String catalogid;

    private String terminal;
    /** 测试用例ID */
    private String testid;
    /** 创建人ID */
    private String userid;
    /** 创建人 */
    private String username;
    /** 修复人ID */
    private String repairid;
    /** 修复人 */
    private String  repairname;
    /** 创建时间 */
    private Date createtime;
    /** 修复时间 */
    private Date repairtime;
    /** 完成/关闭时间 */
    private Date endtime;
    /** 标题 */
    private String title;
    /** 过程 */
    private String process;
    /** 实际结果与不符说明 */
    private String results;
    /** 预期结果 */
    private String expect;
    /** 修复说明 */
    private String repairremark;
    /**
     * 0：待确认：非测试人员提交时，不绑定测试用例，交由测试人员确定BUG真实存在的情况
     * 1：开启：由测试人员提交或测试人员确定BUG真实存在的情况后，BUG绑定测试用例，等待修复人员（指派、领取）修复
     * 2：待测试：修复完成后由修复人员上报修复结果
     * 3：已修复；测试人员测试后确认已修复：如果依然存在则返回开启状态
     * 4：关闭：测试人员确定BUG并不真实存在的情况下关闭并填写理由
     * */
    private Integer status;

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

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getModularid() {
        return modularid;
    }

    public void setModularid(String modularid) {
        this.modularid = modularid;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
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

    public String getRepairid() {
        return repairid;
    }

    public void setRepairid(String repairid) {
        this.repairid = repairid;
    }

    public String getRepairname() {
        return repairname;
    }

    public void setRepairname(String repairname) {
        this.repairname = repairname;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getRepairtime() {
        return repairtime;
    }

    public void setRepairtime(Date repairtime) {
        this.repairtime = repairtime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getExpect() {
        return expect;
    }

    public void setExpect(String expect) {
        this.expect = expect;
    }

    public String getRepairremark() {
        return repairremark;
    }

    public void setRepairremark(String repairremark) {
        this.repairremark = repairremark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }
}
