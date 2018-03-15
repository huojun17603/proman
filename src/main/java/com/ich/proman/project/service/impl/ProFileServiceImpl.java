package com.ich.proman.project.service.impl;

import com.ich.proman.project.mapper.ProFileMapper;
import com.ich.proman.project.service.ProFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProFileServiceImpl implements ProFileService {
    @Autowired
    private ProFileMapper fileMapper;
}
