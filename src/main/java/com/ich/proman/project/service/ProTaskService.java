package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProTask;

import java.util.List;

public interface ProTaskService {
    List<ProTask> findListByMid(String id);
}
