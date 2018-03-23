package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProPrototype;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProPrototypeMapper {

    void insert(ProPrototype prototype);

    void updateTitle(@Param("id") String id, @Param("title")String title);

    void updateToHis(@Param("id")String id);

    void updateToDel(@Param("id")String id, @Param("deletecauses")String deletecauses);

    ProPrototype selectById(@Param("id")String id);

    List<ProPrototype> selectListByMid(@Param("modularid")String modularid);

    List<ProPrototype> selectNormalListByMid(@Param("modularid")String modularid);

    Integer selectCountNormalListByMid(@Param("modularid")String modularid);

    List<ProPrototype> selectVersions(@Param("id")String id);

}
