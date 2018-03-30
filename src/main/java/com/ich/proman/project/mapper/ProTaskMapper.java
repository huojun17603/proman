package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProTaskMapper {

    void insert(ProTask task);

    void delete(@Param("id") String id);

    void updateBase(ProTask task);

    void updateTaskToReceive(@Param("id") String id,@Param("receiveid") String receiveid,@Param("receivename") String receivename);

    void updateTaskToComplete(@Param("id") String id);

    ProTask selectById(@Param("id") String id);

    ProTask selectByPCode(@Param("projectid") String projectid, @Param("code") String code);

    List<ProTask> selectByMid(@Param("modularid") String modularid);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);

    Integer selectCountListByQuery(Map<String, Object> paramMap);
}
