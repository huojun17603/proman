package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProDesign;

import java.util.List;

public interface ProDesignService {
    List<ProDesign> findListByMid(String id);
}
