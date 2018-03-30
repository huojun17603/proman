package com.ich.proman.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.Constant;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.ProTaskMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProRole;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProRoleService;
import com.ich.proman.project.service.ProTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProTaskServiceImpl implements ProTaskService {

    @Autowired
    private ProTaskMapper taskMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addTask(ProTask task) {
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(task.getModularid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择一个模块！");
        if(ObjectHelper.isEmpty(task.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"标题不可为空！");
        if(ObjectHelper.isEmpty(task.getContent()))return new HttpResponse(HttpResponse.HTTP_ERROR,"内容不可为空！");
        if(ObjectHelper.isEmpty(task.getCode()))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务编码不可空！");
        ProTask res = taskMapper.selectByPCode(task.getProjectid(),task.getCode());
        if(ObjectHelper.isNotEmpty(res)) return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的任务编码！");
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        task.setId(IDUtils.createUUId());
        task.setUserid(employee.getEmployeeId());
        task.setUsername(employee.getEmployeeName());
        task.setCreatetime(day);
        task.setStatus(1);
        taskMapper.insert(task);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),task.getTitle(),task.getCode()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_TASK_ADD,message_args),PMessage.PROJECT_TASK_ADD,task.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editTask(ProTask task) {
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(task.getId()))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        ProTask res = taskMapper.selectById(task.getId());
        if(ObjectHelper.isEmpty(res)||res.getStatus()==3) return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        if(ObjectHelper.isEmpty(task.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"标题不可为空！");
        if(ObjectHelper.isEmpty(task.getContent()))return new HttpResponse(HttpResponse.HTTP_ERROR,"内容不可为空！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        task.setUserid(employee.getEmployeeId());
        task.setUsername(employee.getEmployeeName());
        taskMapper.updateBase(task);
        if(ObjectHelper.isNotEmpty(res.getReceiveid())){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),task.getTitle(),task.getCode()};
            messageService.sendMessageToId(res.getReceiveid(), PMessage.findTemplate(PMessage.PROJECT_TASK_EDIT,message_args),PMessage.PROJECT_TASK_EDIT,task.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse delTask(String id) {
        if(ObjectHelper.isEmpty(id))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        ProTask task = taskMapper.selectById(id);
        if(ObjectHelper.isEmpty(task)||task.getStatus()==3) return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        taskMapper.delete(id);
        List<ProRole> roles = roleService.findProRole(project.getId());
        for(ProRole role : roles){
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),task.getTitle(),task.getCode()};
            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_TASK_DEL,message_args),PMessage.PROJECT_TASK_DEL,task.getId());
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editTaskToAppoint(String id, String userid, String username) {
        if(ObjectHelper.isEmpty(id))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        ProTask task = taskMapper.selectById(id);
        if(ObjectHelper.isEmpty(task)||task.getStatus()==3) return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        taskMapper.updateTaskToReceive(id,userid,username);
        String message_args[] = new String[]{project.getTitle(),project.getVersion(),task.getTitle(),task.getCode(),username};
        messageService.sendMessageToId(userid, PMessage.findTemplate(PMessage.PROJECT_TASK_APPOINT,message_args),PMessage.PROJECT_TASK_APPOINT,task.getId());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editTaskToReceive(String id) {
        if(ObjectHelper.isEmpty(id))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        ProTask task = taskMapper.selectById(id);
        if(ObjectHelper.isEmpty(task)||task.getStatus()==3) return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        taskMapper.updateTaskToReceive(id,employee.getEmployeeId(),employee.getEmployeeName());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editTaskToComplete(String id) {
        if(ObjectHelper.isEmpty(id))return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        ProTask task = taskMapper.selectById(id);
        if(ObjectHelper.isEmpty(task)||task.getStatus()==3) return new HttpResponse(HttpResponse.HTTP_ERROR,"任务信息错误！");
        if(ObjectHelper.isEmpty(task.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(task.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        if(!employee.getEmployeeId().equals(task.getReceiveid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"只有当前领取人才能完成此任务！");
        taskMapper.updateTaskToComplete(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProTask> findListByMid(String modularid) {
        return taskMapper.selectByMid(modularid);
    }

    @Override
    public List<Map<String,Object>> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getModularid()))paramMap.put("modularid", query.getModularid());
        if(ObjectHelper.isNotEmpty(query.getUserid()))paramMap.put("userid", query.getUserid());
        if(ObjectHelper.isNotEmpty(query.getStatus()))paramMap.put("status", query.getStatus());
        List<Map<String,Object>> list = taskMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public Integer findCountListByQuery(ProjectQuery query) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getModularid()))paramMap.put("modularid", query.getModularid());
        if(ObjectHelper.isNotEmpty(query.getUserid()))paramMap.put("userid", query.getUserid());
        if(ObjectHelper.isNotEmpty(query.getStatus()))paramMap.put("status", query.getStatus());
        return taskMapper.selectCountListByQuery(paramMap);
    }
}
