package com.ich.proman.project.service.impl;

import com.ich.proman.project.mapper.ProPrototypeTagMapper;
import com.ich.proman.project.service.ProPrototypeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProPrototypeTagServiceImpl implements ProPrototypeTagService {

    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;

}
