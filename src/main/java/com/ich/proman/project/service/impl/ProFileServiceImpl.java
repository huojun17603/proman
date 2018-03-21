package com.ich.proman.project.service.impl;

import com.ich.proman.project.mapper.ProFileMapper;
import com.ich.proman.project.pojo.ProFile;
import com.ich.proman.project.service.ProFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProFileServiceImpl implements ProFileService {
    @Autowired
    private ProFileMapper fileMapper;

    @Override
    public List<ProFile> findListByMid(String id) {
        return null;
    }
}
