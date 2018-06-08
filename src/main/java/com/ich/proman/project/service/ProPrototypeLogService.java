package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProPrototypeLog;

import java.util.List;

public interface ProPrototypeLogService {

    HttpResponse insertLog(ProPrototypeLog log);

    List<?> findListByQuery(PageView view, ProjectQuery query);


}
