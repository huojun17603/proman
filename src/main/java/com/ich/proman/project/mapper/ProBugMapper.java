package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProBug;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProBugMapper {

    void insert(ProBug test);

    ProBug selectById(@Param("id") String id);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);

    List<ProBug> selectListByMid(@Param("modularid") String modularid);

    void updateToOpen(ProBug entity);

    void updateToTest(@Param("id")String id, @Param("repairremark")String repairremark);

    void updateToCom(@Param("id")String id);

    void updateToReopen(@Param("id")String id);

    void updateToColse(@Param("id")String id, @Param("repairremark")String repairremark);

    void updateBUGToReceive(@Param("id")String id, @Param("repairid")String repairid, @Param("repairname")String repairname);
}
