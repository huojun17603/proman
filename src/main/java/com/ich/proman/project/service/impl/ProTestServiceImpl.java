package com.ich.proman.project.service.impl;

import com.ich.proman.project.pojo.ProTest;
import com.ich.proman.project.service.ProTestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProTestServiceImpl implements ProTestService {
    @Override
    public List<ProTest> findListByMid(String id) {
        return null;
    }
}
