package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProTask;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProTaskService {

    HttpResponse addTask(ProTask task);

    HttpResponse editTask(ProTask task);

    HttpResponse delTask(String id);
    /** 指派任务 */
    HttpResponse editTaskToAppoint(String id, Date estimatetime, String userid, String username);
    /** 领取任务 */
    HttpResponse editTaskToReceive(String id,Date estimatetime);
    /** 完成任务 */
    HttpResponse editTaskToComplete(String id);

    /** 获取模块下所有的任务列表（用户验证） */
    List<ProTask> findListByMid(String modularid);

    List<Map<String,Object>> findListByQuery(PageView view, ProjectQuery query);

    Integer findCountListByQuery(ProjectQuery query);
}
