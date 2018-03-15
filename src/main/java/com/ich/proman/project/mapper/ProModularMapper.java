package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProModular;

public interface ProModularMapper {

    void insert(ProModular modular);

    ProModular selectById(String id);

    void updateModularName(String id, String modularname);

    void delete(String id);
}
