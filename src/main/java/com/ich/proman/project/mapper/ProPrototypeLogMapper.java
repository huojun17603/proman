package com.ich.proman.project.mapper;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProPrototypeLog;

import java.util.List;
import java.util.Map;

public interface ProPrototypeLogMapper {

    void insert(ProPrototypeLog log);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);
}
