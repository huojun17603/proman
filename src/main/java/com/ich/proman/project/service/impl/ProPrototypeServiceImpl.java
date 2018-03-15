package com.ich.proman.project.service.impl;

import com.ich.proman.project.mapper.ProPrototypeMapper;
import com.ich.proman.project.service.ProPrototypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProPrototypeServiceImpl implements ProPrototypeService {

    @Autowired
    private ProPrototypeMapper prototypeMapper;

}
