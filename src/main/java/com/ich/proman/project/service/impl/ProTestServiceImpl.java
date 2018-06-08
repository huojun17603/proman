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
import com.ich.proman.project.controller.TerminalController;
import com.ich.proman.project.mapper.ProModularMapper;
import com.ich.proman.project.mapper.ProTestMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProModular;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.ProTest;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProTestServiceImpl implements ProTestService {

    @Autowired
    private ProTestMapper testMapper;
    @Autowired
    private ProModularMapper modularMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public HttpResponse addTest(ProTest test) {
        if(ObjectHelper.isEmpty(test.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(test.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(test.getModularid()))return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择一个模块！");
        ProModular modular = modularMapper.selectById(test.getModularid());
        if(ObjectHelper.isEmpty(modular))return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息！");
        if(ObjectHelper.isEmpty(test.getTerminal())
                || !TerminalController.vryTerminals(test.getTerminal()))
            return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的模块信息！");

        if(ObjectHelper.isEmpty(test.getCode()))return new HttpResponse(HttpResponse.HTTP_ERROR,"编码不可空！");
        if(ObjectHelper.isEmpty(test.getTitle()))return new HttpResponse(HttpResponse.HTTP_ERROR,"标题不可为空！");
        if(ObjectHelper.isEmpty(test.getProcess()))return new HttpResponse(HttpResponse.HTTP_ERROR,"过程不可为空！");
        if(ObjectHelper.isEmpty(test.getResults()))return new HttpResponse(HttpResponse.HTTP_ERROR,"结果不可为空！");
        ProTest res = testMapper.selectByPCode(test.getProjectid(),test.getCode());
        if(ObjectHelper.isNotEmpty(res)) return new HttpResponse(HttpResponse.HTTP_ERROR,"已存在的任务编码！");
        Date day = new Date();
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        test.setId(IDUtils.createUUId());
        test.setUserid(employee.getEmployeeId());
        test.setUsername(employee.getEmployeeName());
        test.setCatalogid(modular.getCatalogid());
        test.setCreatetime(day);
        test.setStatus(1);
        testMapper.insert(test);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editTest(ProTest test) {
        ProTest res = testMapper.selectById(test.getId());
        if(ObjectHelper.isEmpty(res)) return new HttpResponse(HttpResponse.HTTP_ERROR,"错误的用例信息！");
        Project project = projectCoreMapper.selectById(res.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isNotEmpty(test.getTitle())) res.setTitle(test.getTitle());
        if(ObjectHelper.isNotEmpty(test.getProcess())) res.setProcess(test.getProcess());
        if(ObjectHelper.isNotEmpty(test.getResults())) res.setResults(test.getResults());
        testMapper.update(test);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse delTest(String id) {
        ProTest res = testMapper.selectById(id);
        if(ObjectHelper.isEmpty(res)) return new HttpResponse(HttpResponse.HTTP_ERROR,"错误的用例信息！");
        Project project = projectCoreMapper.selectById(res.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        testMapper.updateToDel(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public ProTest findById(String id) {
        return testMapper.selectById(id);
    }

    @Override
    public List<Map<String, Object>> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        if(ObjectHelper.isNotEmpty(query.getModularid()))paramMap.put("modularid", query.getModularid());
        if(ObjectHelper.isNotEmpty(query.getUserid()))paramMap.put("userid", query.getUserid());
        if(ObjectHelper.isNotEmpty(query.getStatus()))paramMap.put("status", query.getStatus());
        if(ObjectHelper.isNotEmpty(query.getTerminal()))paramMap.put("terminal", query.getTerminal());
        if(ObjectHelper.isNotEmpty(query.getCatalogid()))paramMap.put("catalogid", query.getCatalogid());
        if(ObjectHelper.isNotEmpty(query.getOrder())){
            paramMap.put("order", query.getOrder());
        }else {
            paramMap.put("order", "1");
        }
        List<Map<String,Object>> list = testMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public List<ProTest> findListByMid(String modularid) {
        return testMapper.selectListByMid(modularid);
    }
}
