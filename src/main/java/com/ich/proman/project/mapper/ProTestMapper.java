package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProTestMapper {

    void insert(ProTest test);

    void update(ProTest test);

    void updateToDel(@Param("id") String id);

    ProTest selectById(@Param("id") String id);

    ProTest selectByPCode(@Param("projectid") String projectid, @Param("code") String code);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);

    List<ProTest> selectListByMid(@Param("modularid") String modularid);
}
