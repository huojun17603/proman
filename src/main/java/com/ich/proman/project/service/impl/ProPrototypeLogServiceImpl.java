package com.ich.proman.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.mapper.ProPrototypeLogMapper;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeLog;
import com.ich.proman.project.service.ProPrototypeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProPrototypeLogServiceImpl implements ProPrototypeLogService {

    @Autowired
    private ProPrototypeLogMapper prototypeLogMapper;

    @Override
    public HttpResponse insertLog(ProPrototypeLog log) {
        prototypeLogMapper.insert(log);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<?> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getCatalogid()))paramMap.put("catalogid", query.getCatalogid());
        List<Map<String,Object>> list = prototypeLogMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }
}
