package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.pojo.ProTest;

import java.util.List;
import java.util.Map;

public interface ProTestService {

    HttpResponse addTest(ProTest test);

    HttpResponse editTest(ProTest test);

    HttpResponse delTest(String id);

    ProTest findById(String id);

    List<Map<String,Object>> findListByQuery(PageView view, ProjectQuery query);

    List<ProTest> findListByMid(String modularid);

}
