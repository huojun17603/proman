package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProRole;

import java.util.List;

public interface ProRoleMapper {
    void insert(ProRole role);

    ProRole selectById(String id);

    void delete(String id);

    List<ProRole> selectByProId(String projectid);
}
