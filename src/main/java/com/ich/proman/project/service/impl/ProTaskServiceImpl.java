package com.ich.proman.project.service.impl;

import com.ich.proman.project.pojo.ProTask;
import com.ich.proman.project.service.ProTaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProTaskServiceImpl implements ProTaskService {

    @Override
    public List<ProTask> findListByMid(String id) {
        return null;
    }
}
