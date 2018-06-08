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
import com.ich.proman.project.controller.TerminalController;
import com.ich.proman.project.mapper.*;
import com.ich.proman.project.pojo.*;
import com.ich.proman.project.service.ProBugService;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProBugServiceImpl implements ProBugService {

    @Autowired
    private ProBugMapper bugMapper;
    @Autowired
    private ProTestMapper testMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addBUG(ProBug bug) {
        if(ObjectHelper.isEmpty(bug.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(bug.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(bug.getResults()))return new HttpResponse(HttpResponse.HTTP_ERROR,"实际结果不可为空！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        ProRole role = roleService.selectByUseridAndRole(bug.getProjectid(),localEmployee.getEmployeeId(),ProRole.PROJECT_TEST);
        bug.setId(IDUtils.createUUId());
        bug.setUserid(localEmployee.getEmployeeId());
        bug.setUsername(localEmployee.getEmployeeName());
        bug.setCreatetime(new Date());
        if(ObjectHelper.isNotEmpty(role)){
            if(ObjectHelper.isEmpty(bug.getTestid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"用例不可空");
            ProTest test = testMapper.selectById(bug.getTestid());
            bug.setCatalogid(test.getCatalogid());
            bug.setTerminal(test.getTerminal());
            bug.setTitle(test.getTitle());
            bug.setProcess(test.getProcess());
            bug.setExpect(test.getResults());
            bug.setStatus(1);
            bug.setModularid(test.getModularid());
        }else{
            if(ObjectHelper.isEmpty(bug.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"标题不可为空！");
            if(ObjectHelper.isEmpty(bug.getProcess()))return new HttpResponse(HttpResponse.HTTP_ERROR,"过程不可为空！");
            if(ObjectHelper.isEmpty(bug.getExpect()))return new HttpResponse(HttpResponse.HTTP_ERROR,"预期结果不可为空！");
            bug.setStatus(0);
            //通知测试
            List<ProRole> roles = roleService.findProRoles(bug.getProjectid());
            for(ProRole role2 : roles){//通知测试人员测试
                if(role2.getRole()==ProRole.PROJECT_TEST){
                    String message_args[] = new String[]{project.getTitle(),project.getVersion(),bug.getTitle()};
                    messageService.sendMessageToId(role2.getUserid(), PMessage.findTemplate(PMessage.PROJECT_BUG_CREATE,message_args),PMessage.PROJECT_BUG_CREATE,bug.getId());
                }
            }
        }
        this.bugMapper.insert(bug);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editToOpen(ProBug bug) {
        ProBug entity = this.bugMapper.selectById(bug.getId());
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=0) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的BUG信息！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        ProRole role = roleService.selectByUseridAndRole(entity.getProjectid(),localEmployee.getEmployeeId(),ProRole.PROJECT_TEST);
        if(ObjectHelper.isNotEmpty(role)){
            if(ObjectHelper.isEmpty(bug.getTestid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"用例不可空");
            ProTest test = testMapper.selectById(bug.getTestid());
            entity.setCatalogid(test.getCatalogid());
            entity.setTerminal(test.getTerminal());
            entity.setTestid(test.getId());
            entity.setTitle(test.getTitle());
            entity.setProcess(test.getProcess());
            entity.setExpect(test.getResults());
            entity.setModularid(test.getModularid());
            this.bugMapper.updateToOpen(entity);
        }else{
            return new HttpResponse(HttpResponse.HTTP_ERROR,"你不是测试人员不能操作！");
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editToTest(String id, String repairremark) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=1) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的BUG信息！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(repairremark))return new HttpResponse(HttpResponse.HTTP_ERROR,"修复说明不可空！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        if(!localEmployee.getEmployeeId().equals(entity.getRepairid()))
            return new HttpResponse(HttpResponse.HTTP_ERROR,"错误的修复人信息！");
        this.bugMapper.updateToTest(id,repairremark);
        List<ProRole> roles = roleService.findProRoles(entity.getProjectid());
        for(ProRole role : roles){//通知测试人员测试
            if(role.getRole()==ProRole.PROJECT_TEST){
                String message_args[] = new String[]{project.getTitle(),project.getVersion(),entity.getTitle()};
                messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_BUG_TEST,message_args),PMessage.PROJECT_BUG_TEST,id);
            }
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editToCom(String id) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=2) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的BUG信息！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        ProRole role = roleService.selectByUseridAndRole(entity.getProjectid(),localEmployee.getEmployeeId(),ProRole.PROJECT_TEST);
        if(ObjectHelper.isNotEmpty(role)){
            this.bugMapper.updateToCom(id);
            //通知领取人
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),entity.getTitle()};
            messageService.sendMessageToId(entity.getRepairid(), PMessage.findTemplate(PMessage.PROJECT_BUG_COM,message_args),PMessage.PROJECT_BUG_COM,id);

        }else{
            return new HttpResponse(HttpResponse.HTTP_ERROR,"你不是测试人员不能操作！");
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editToReopen(String id) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=2) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的BUG信息！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        ProRole role = roleService.selectByUseridAndRole(entity.getProjectid(),localEmployee.getEmployeeId(),ProRole.PROJECT_TEST);
        if(ObjectHelper.isNotEmpty(role)){
            this.bugMapper.updateToReopen(id);
            //通知领取人
            String message_args[] = new String[]{project.getTitle(),project.getVersion(),entity.getTitle()};
            messageService.sendMessageToId(entity.getRepairid(), PMessage.findTemplate(PMessage.PROJECT_BUG_REOPEN,message_args),PMessage.PROJECT_BUG_REOPEN,id);
        }else{
            return new HttpResponse(HttpResponse.HTTP_ERROR,"你不是测试人员不能操作！");
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editToColse(String id, String repairremark) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的BUG信息！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(repairremark))return new HttpResponse(HttpResponse.HTTP_ERROR,"关闭说明不可空！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        ProRole role = roleService.selectByUseridAndRole(entity.getProjectid(),localEmployee.getEmployeeId(),ProRole.PROJECT_TEST);
        if(ObjectHelper.isNotEmpty(role)){
            this.bugMapper.updateToColse(id,repairremark);
            //通知领取人
            if(ObjectHelper.isNotEmpty(entity.getRepairid())) {
                String message_args[] = new String[]{project.getTitle(), project.getVersion(), entity.getTitle()};
                messageService.sendMessageToId(entity.getRepairid(), PMessage.findTemplate(PMessage.PROJECT_BUG_CLOSE, message_args), PMessage.PROJECT_BUG_CLOSE, id);
            }

        }else{
            return new HttpResponse(HttpResponse.HTTP_ERROR,"你不是测试人员不能操作！");
        }

        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editBUGToAppoint(String id, String userid, String username) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=1) return new HttpResponse(HttpResponse.HTTP_ERROR,"只有开启的BUG才可领取！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        bugMapper.updateBUGToReceive(id,userid,username);
        //通知领取人
        String message_args[] = new String[]{project.getTitle(),project.getVersion(),entity.getTitle()};
        messageService.sendMessageToId(userid, PMessage.findTemplate(PMessage.PROJECT_BUG_APPOINT,message_args),PMessage.PROJECT_BUG_APPOINT,id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editBUGToReceive(String id) {
        ProBug entity = this.bugMapper.selectById(id);
        if(ObjectHelper.isEmpty(entity)||entity.getStatus()!=1) return new HttpResponse(HttpResponse.HTTP_ERROR,"只有开启的BUG才可领取！");
        Project project = projectCoreMapper.selectById(entity.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        LocalEmployee localEmployee = localEmployeeServiceImpl.findLocalEmployee();
        bugMapper.updateBUGToReceive(id,localEmployee.getEmployeeId(),localEmployee.getEmployeeName());
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public ProBug findById(String id) {
        return bugMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getModularid()))paramMap.put("modularid", query.getModularid());
        if(ObjectHelper.isNotEmpty(query.getUserid()))paramMap.put("repairid", query.getUserid());
        if(ObjectHelper.isNotEmpty(query.getStatus()))paramMap.put("status", query.getStatus());
        if(ObjectHelper.isNotEmpty(query.getTerminal()))paramMap.put("terminal", query.getTerminal());
        if(ObjectHelper.isNotEmpty(query.getCatalogid()))paramMap.put("catalogid", query.getCatalogid());
        if(ObjectHelper.isNotEmpty(query.getOrder())){
            paramMap.put("order", query.getOrder());
        }else{
            paramMap.put("order", "1");
        }
        List<Map<String,Object>> list = bugMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public List<ProBug> findListByMid(String modularid) {
        return bugMapper.selectListByMid(modularid);
    }
}
