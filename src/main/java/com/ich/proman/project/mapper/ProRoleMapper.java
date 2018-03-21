package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProRoleMapper {

    void insert(ProRole role);

    void delete(@Param("id") String id);

    ProRole selectById(@Param("id") String id);

    List<ProRole> selectByProId(@Param("projectid") String projectid);
}
