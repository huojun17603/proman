package com.ich.proman.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.LocalEmployeeService;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.Constant;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.mapper.*;
import com.ich.proman.project.pojo.*;
import com.ich.proman.project.service.ProPrototypeService;
import com.ich.proman.project.service.ProRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProPrototypeServiceImpl implements ProPrototypeService {

    @Autowired
    private ProPrototypeMapper prototypeMapper;
    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;
    @Autowired
    private ProPrototypeLogMapper prototypeLogMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private ProRoleService roleService;
    @Autowired
    private PMessageService messageService;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addPrototype(ProPrototype prototype) {
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(prototype.getCatalogid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择一个目录！");
        if(ObjectHelper.isEmpty(prototype.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"原型标题不可为空！");
        if(ObjectHelper.isEmpty(prototype.getImg()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传原型图！");
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        prototype.setId(IDUtils.createUUId());
        prototype.setUserid(employee.getEmployeeId());
        prototype.setUsername(employee.getEmployeeName());
        prototype.setCreatetime(day);
        prototype.setGroupid(prototype.getId());
        prototype.setGroupstatus(Constant.STATUS_NORMAL);
        prototypeMapper.insert(prototype);
        ProPrototypeLog log = new ProPrototypeLog();
        log.setId(IDUtils.createUUId());
        log.setCatalogid(prototype.getCatalogid());
        log.setCreatetime(day);
        log.setLogclass(1);
        log.setPrototypeid(prototype.getId());
        log.setPrototypetitle(prototype.getTitle());
        log.setUserid(employee.getEmployeeId());
        log.setUsername(employee.getEmployeeName());
        log.setRemark("新增原型“"+prototype.getTitle()+"”");
        prototypeLogMapper.insert(log);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK,prototype);
    }

    @Override
    public HttpResponse editPrototypeTitle(String id, String title) {
        ProPrototype prototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        prototypeMapper.updateTitle(id,title);
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        ProPrototypeLog log = new ProPrototypeLog();
        log.setId(IDUtils.createUUId());
        log.setCatalogid(prototype.getCatalogid());
        log.setCreatetime(day);
        log.setLogclass(1);
        log.setPrototypeid(prototype.getId());
        log.setPrototypetitle(prototype.getTitle());
        log.setUserid(employee.getEmployeeId());
        log.setUsername(employee.getEmployeeName());
        log.setRemark("修改原型标题“"+prototype.getTitle()+"”->“"+title+"”");
        prototypeLogMapper.insert(log);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPrototypeToImg(String id, String img, String iterationcauses,Boolean imports) {
        ProPrototype oprototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(oprototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(oprototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(img))return new HttpResponse(HttpResponse.HTTP_ERROR,"请上传原型图！");
        prototypeMapper.updateToHis(id);
        ProPrototype prototype = new ProPrototype();
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        prototype.setId(IDUtils.createUUId());
        prototype.setTitle(oprototype.getTitle());
        prototype.setProjectid(oprototype.getProjectid());
        prototype.setCatalogid(oprototype.getCatalogid());
        prototype.setImg(img);
        prototype.setUserid(employee.getEmployeeId());
        prototype.setUsername(employee.getEmployeeName());
        prototype.setCreatetime(day);
        prototype.setGroupid(oprototype.getGroupid());
        prototype.setGroupstatus(Constant.STATUS_NORMAL);
        prototype.setIterationcauses(iterationcauses);
        prototypeMapper.insert(prototype);
        //引入
        List<ProPrototypeTag> tagss = this.prototypeTagMapper.selectNormalListByPid(oprototype.getId());
        for(ProPrototypeTag tag : tagss){
            List<ProPrototypeTag> tags = prototypeTagMapper.selectVersions(tag.getGroupid());//被选中的标记的历史标记列表
            String tid = IDUtils.createUUId();
            for(ProPrototypeTag obj : tags){
                if(!tag.getId().equals(obj.getId())) {
                    obj.setId(IDUtils.createUUId());
                    obj.setPrototypeid(prototype.getId());
                    obj.setGroupid(tid);
                    this.prototypeTagMapper.insert(obj);
                }
            }
            tag.setId(tid);
            tag.setPrototypeid(prototype.getId());
            tag.setGroupid(tid);
            this.prototypeTagMapper.insert(tag);
        }
        //修改跳转
        prototypeTagMapper.updateTZForAll(id,prototype.getId());
//        List<ProRole> roles = roleService.findProRole(project.getId());
//        for(ProRole role : roles){
//            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle()};
//            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_EDIT_IMG,message_args),PMessage.PROJECT_PROTOTYPE_EDIT_IMG,prototype.getId());
//        }

        ProPrototypeLog log = new ProPrototypeLog();
        log.setId(IDUtils.createUUId());
        log.setCatalogid(prototype.getCatalogid());
        log.setCreatetime(day);
        log.setLogclass(1);
        log.setPrototypeid(prototype.getId());
        log.setPrototypetitle(prototype.getTitle());
        log.setUserid(employee.getEmployeeId());
        log.setUsername(employee.getEmployeeName());
        log.setRemark("原型“"+prototype.getTitle()+"”已迭代版本");
        prototypeLogMapper.insert(log);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPrototypeToDel(String id, String deletecauses) {
        ProPrototype prototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        prototypeMapper.updateToDel(id,deletecauses);
//        List<ProRole> roles = roleService.findProRole(project.getId());
//        for(ProRole role : roles){
//            String message_args[] = new String[]{project.getTitle(),project.getVersion(),prototype.getTitle()};
//            messageService.sendMessageToId(role.getUserid(), PMessage.findTemplate(PMessage.PROJECT_PROTOTYPE_DEL,message_args),PMessage.PROJECT_PROTOTYPE_DEL,prototype.getId());
//        }
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        ProPrototypeLog log = new ProPrototypeLog();
        log.setId(IDUtils.createUUId());
        log.setCatalogid(prototype.getCatalogid());
        log.setCreatetime(day);
        log.setLogclass(1);
        log.setPrototypeid(prototype.getId());
        log.setPrototypetitle(prototype.getTitle());
        log.setUserid(employee.getEmployeeId());
        log.setUsername(employee.getEmployeeName());
        log.setRemark("原型“"+prototype.getTitle()+"”已标记为废弃");
        prototypeLogMapper.insert(log);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<ProPrototype> findNormalListByPCid(String catalogid) {
        return prototypeMapper.selectNormalListByPCid(catalogid);
    }

    @Override
    public Integer findCountNormalListByPCid(String catalogid) {
        return prototypeMapper.selectCountNormalListByPCid(catalogid);
    }

    @Override
    public List<ProPrototype> findVersions(String id) {
        return prototypeMapper.selectVersions(id);
    }

    @Override
    public List<?> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getCatalogid()))paramMap.put("catalogid", query.getCatalogid());
        if(ObjectHelper.isNotEmpty(query.getStatus()))paramMap.put("status", query.getStatus());
        List<Map<String,Object>> list = prototypeMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public ProPrototype findById(String id) {
        return prototypeMapper.selectById(id);
    }

    @Override
    public ProPrototype findDefault(String catalogid) {
        return prototypeMapper.selectDefaultByCatalog(catalogid);
    }

    @Override
    public HttpResponse editPrototypeToDefault(String id) {
        ProPrototype prototype = prototypeMapper.selectById(id);
        if(ObjectHelper.isEmpty(prototype.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(prototype.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        prototypeMapper.updateAllToNoDefault();
        prototypeMapper.updateDefaultById(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }
}
