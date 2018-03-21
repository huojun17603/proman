package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProTest;

import java.util.List;

public interface ProTestService {
    List<ProTest> findListByMid(String id);
}
