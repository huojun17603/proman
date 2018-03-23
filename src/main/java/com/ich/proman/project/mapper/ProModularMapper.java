package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProModular;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProModularMapper {

    void insert(ProModular modular);

    ProModular selectById(@Param("id") String id);

    void updateModularName(@Param("id") String id, @Param("modularname") String modularname);

    void delete(@Param("id") String id);

    List<ProModular> selectByName(@Param("modularname") String modularname);

    Map<String,Object> selectModularDetailById(@Param("id")String id);
}
