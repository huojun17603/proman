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
    /** 模块：新增 */
    public static Integer PROJECT_MODULAR_CREATE = 300;
    /** 模块：修改名称 */
    public static Integer PROJECT_MODULAR_EDIT_NAME = 301;
    /** 模块：删除 */
    public static Integer PROJECT_MODULAR_DEL = 302;
    /** 原型：新增 */
    /** 原型：修改标题 */
    /** 原型：迭代图片 */
    /** 原型：新增标记 */
    /** 原型：迭代标记 */

    /** 设计 500*/

    /** 任务 600*/

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
                result = "您已被" + args[0] + "要求参与项目：" + args[1] + "（" + args[2] + "），请开始您的表演！";
            }
            if (PROJECT_ROLE_DEL.equals(source)) {
                result = "您已被" + args[0] + "要求退出项目：" + args[1] + "（" + args[2] + "）。";
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
            /*//*/
        }catch (Exception e){
            throw new CustomException(HttpResponse.HTTP_ERROR, "PMessage.findTemplate:args length error");
        }
        return result;
    }


}
