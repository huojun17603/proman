package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProPrototype;

import java.util.List;

public interface ProPrototypeService {
    List<ProPrototype> findListByMid(String id);
}
