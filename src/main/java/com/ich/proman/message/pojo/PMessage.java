package com.ich.proman.message.pojo;

import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.other.CustomException;

import java.util.Date;

/**
 * 提示消息
 * 项目变更后针对不同的角色发送确认消息
 */
public class PMessage {

    private String id;

    private String userid;

    private Date createtime;

    private Integer status;

    private Date confirmtime;

    private String content;

    private Integer source;

    private String sourceid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getConfirmtime() {
        return confirmtime;
    }

    public void setConfirmtime(Date confirmtime) {
        this.confirmtime = confirmtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public String getSourceid() {
        return sourceid;
    }

    public void setSourceid(String sourceid) {
        this.sourceid = sourceid;
    }
    public static Integer UNREAD = 0;

    public static Integer READ = 1;

    //*定义Source*//
    /** 项目立项 */
    public static Integer PROJECT_CREATE = 100;
    /** 项目修改：名称 */
    public static Integer PROJECT_EDIT_NAME = 101;
    /** 项目标记：废弃 */
    public static Integer PROJECT_EDIT_DISABLE = 102;
    /** 项目标记：历史 */
    public static Integer PROJECT_EDIT_HIS = 103;
    /** 人员：参与项目 */
    public static Integer PROJECT_ROLE_ADD = 200;
    /** 人员：退出项目 */
    public static Integer PROJECT_ROLE_DEL = 201;
    /** 人员：参与项目（通知其他参与者） */
    public static Integer PROJECT_ROLE_ADD_NOTICE = 202;
    /** 人员：退出项目（通知其他参与者） */
    public static Integer PROJECT_ROLE_DEL_NOTICE = 203;
    /** 模块：新增 */
    public static Integer PROJECT_MODULAR_CREATE = 300;
    /** 模块：修改名称 */
    public static Integer PROJECT_MODULAR_EDIT_NAME = 301;
    /** 模块：删除 */
    public static Integer PROJECT_MODULAR_DEL = 302;
    /** 原型：新增 */
    public static Integer PROJECT_PROTOTYPE_ADD = 400;
    /** 原型：修改标题 */
    public static Integer PROJECT_PROTOTYPE_EDIT_TITLE = 401;
    /** 原型：迭代图片 */
    public static Integer PROJECT_PROTOTYPE_EDIT_IMG = 402;
    /** 原型：删除 */
    public static Integer PROJECT_PROTOTYPE_DEL = 403;
    /** 原型：新增标记 */
    public static Integer PROJECT_PROTOTYPE_TAG_ADD = 404;
    /** 原型：迭代标记 */
    public static Integer PROJECT_PROTOTYPE_TAG_EDIT = 405;
    /** 原型：引入标记 */
    public static Integer PROJECT_PROTOTYPE_TAG_IMPORT = 406;

    /** 设计 500*/

    /** 任务:新增*/
    public static Integer PROJECT_TASK_ADD = 600;
    /** 任务:修改*/
    public static Integer PROJECT_TASK_EDIT = 601;
    /** 任务:删除*/
    public static Integer PROJECT_TASK_DEL = 602;
    /** 任务:领取*/
    public static Integer PROJECT_TASK_RECEIVE = 603;
    /** 任务:完成*/
    public static Integer PROJECT_TASK_COMPLETE = 604;
    /** 任务:指派*/
    public static Integer PROJECT_TASK_APPOINT = 605;

    /** 用例 ：好像没有通知的必要*/

    /** BUG:新增 */
    public static Integer PROJECT_BUG_CREATE = 800;
    /** BUG:确认 */

    /** 文件：新增 */
    public static Integer PROJECT_FILE_CREATE = 900;
    /** 文件：迭代 */
    public static Integer PROJECT_FILE_ITE = 901;
    /** 文件：标记废弃 */
    public static Integer PROJECT_FILE_DISABLE = 902;

    //*定义内容模板*//
    public static String findTemplate(Integer source,String args[]){
        if(ObjectHelper.isEmpty(source)) throw new CustomException(HttpResponse.HTTP_ERROR,"PMessage.findTemplate:source is NULL");
        String result = "";
        //构建项目立项的内容模板
        try {
            /*/项目/*/
            if (PROJECT_CREATE.equals(source)) {
                result = "项目：" + args[0] + "已立项，当前版本为：" + args[1];
            }
            if (PROJECT_EDIT_NAME.equals(source)) {
                //args[0]为项目原名称
                result = "项目：" + args[0] + "（" + args[1] + "）修改名称为：" + args[2];
            }
            if (PROJECT_EDIT_DISABLE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "）标记为：废弃";
            }
            if (PROJECT_EDIT_HIS.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "）标记为：历史";
            }
            /*/项目：参与人/*/
            if (PROJECT_ROLE_ADD.equals(source)) {
                result = "您已被要求参与项目：" + args[0] + "（" + args[1] + "），请开始您的表演！";
            }
            if (PROJECT_ROLE_DEL.equals(source)) {
                result = "您已被要求退出项目：" + args[0] + "（" + args[1] + "）。";
            }
            if (PROJECT_ROLE_ADD_NOTICE.equals(source)) {
                result = args[2] + "（" + args[3] + "）加入项目：" + args[0] + "（" + args[1] + "），大家热烈欢迎！";
            }
            if (PROJECT_ROLE_DEL_NOTICE.equals(source)) {
                result = args[2] + "（" + args[3] + "）退出项目：" + args[0] + "（" + args[1] + "），真是遗憾啊！";
            }
            /*/项目：模块/*/
            if (PROJECT_MODULAR_CREATE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），新增模块："+ args[2];
            }
            if (PROJECT_MODULAR_EDIT_NAME.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），模块“"+ args[2]+"”修改名称为："+args[3];
            }
            if (PROJECT_MODULAR_DEL.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），删除模块："+ args[2];
            }
            /*/项目：原型/*/
            if (PROJECT_PROTOTYPE_ADD.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），新增原型："+ args[2];
            }
            if (PROJECT_PROTOTYPE_EDIT_TITLE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”修改名称为："+args[3];
            }
            if (PROJECT_PROTOTYPE_EDIT_IMG.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”已迭代！";
            }
            if (PROJECT_PROTOTYPE_DEL.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”被标记为删除！";
            }
            if (PROJECT_PROTOTYPE_TAG_ADD.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”新增标记：" + args[3];
            }
            if (PROJECT_PROTOTYPE_TAG_EDIT.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”标记“" + args[3] +"”已被修改！";
            }
            if (PROJECT_PROTOTYPE_TAG_IMPORT.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），原型“"+ args[2]+"”引入标记：" + args[3];
            }
            /*/项目：设计/*/

            /*/项目：任务/*/
            if (PROJECT_TASK_ADD.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），新增任务："+ args[2] + "（"+ args[3] +"）";
            }
            if (PROJECT_TASK_EDIT.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），任务《"+ args[2] + "（"+ args[3] +"）"+"》已被修改！";
            }
            if (PROJECT_TASK_DEL.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），任务《"+ args[2] + "（"+ args[3] +"）"+"》已被废弃！";
            }
            if (PROJECT_TASK_RECEIVE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），任务《"+ args[2] + "（"+ args[3] +"）"+"》已被"+ args[4]+"领取！";
            }
            if (PROJECT_TASK_COMPLETE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），任务《"+ args[2] + "（"+ args[3] +"）"+"》已被"+ args[4]+"完成！";
            }
            if (PROJECT_TASK_APPOINT.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），任务《"+ args[2] + "（"+ args[3] +"）"+"》已被指派给"+ args[4];
            }
            /*/项目：用例/*/

            /*/项目：BUG/*/

            /*/项目：文件/*/
            if (PROJECT_FILE_CREATE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），新增文件："+ args[2];
            }
            if (PROJECT_FILE_ITE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），文件迭代："+ args[2];
            }
            if (PROJECT_FILE_DISABLE.equals(source)) {
                result = "项目：" + args[0] + "（" + args[1] + "），文件删除："+ args[2];
            }
        }catch (Exception e){
            throw new CustomException(HttpResponse.HTTP_ERROR, "PMessage.findTemplate:args length error");
        }
        return result;
    }


}
