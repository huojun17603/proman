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
    public static Integer PROJECT_CREATE = 0;
    /** 人员参与项目 */
    public static Integer PROJECT_ROLE_ADD = 1;
    /** 人员退出项目 */
    public static Integer PROJECT_ROLE_DEL = 2;
    //*定义内容模板*//
    public static String findTemplate(Integer source,String args[]){
        if(ObjectHelper.isEmpty(source)) throw new CustomException(HttpResponse.HTTP_ERROR,"PMessage.findTemplate:source is NULL");
        String result = "";
        //构建项目立项的内容模板
        if(PROJECT_CREATE.equals(source)){
            if(args.length!=1) throw new CustomException(HttpResponse.HTTP_ERROR,"PMessage.findTemplate:args length error");
            result = "项目XXX已立项，当前版本为：XXX";
        }
        if(PROJECT_ROLE_ADD.equals(source)){
            if(args.length!=3) throw new CustomException(HttpResponse.HTTP_ERROR,"PMessage.findTemplate:args length error");
            result = "您已被"+args[0]+"要求参与项目："+args[1]+"（"+args[2]+"），请开始您的表演！";
        }
        if(PROJECT_ROLE_DEL.equals(source)){
            if(args.length!=3) throw new CustomException(HttpResponse.HTTP_ERROR,"PMessage.findTemplate:args length error");
            result = "您已被"+args[0]+"要求退出项目："+args[1]+"（"+args[2]+"）。";
        }
        return result;
    }


}
