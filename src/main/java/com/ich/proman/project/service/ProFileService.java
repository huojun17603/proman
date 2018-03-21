package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProFile;

import java.util.List;

public interface ProFileService {
    List<ProFile> findListByMid(String id);
}
