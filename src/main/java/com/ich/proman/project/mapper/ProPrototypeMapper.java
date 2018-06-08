package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProPrototype;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProPrototypeMapper {

    void insert(ProPrototype prototype);

    void updateTitle(@Param("id") String id, @Param("title")String title);

    void updateToHis(@Param("id")String id);

    void updateToDel(@Param("id")String id, @Param("deletecauses")String deletecauses);

    ProPrototype selectById(@Param("id")String id);

    List<ProPrototype> selectVersions(@Param("id")String id);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);

    List<ProPrototype> selectNormalListByPCid(@Param("catalogid")String catalogid);

    Integer selectCountNormalListByPCid(@Param("catalogid")String catalogid);

    void updateDefaultById(@Param("id")String id);

    void updateAllToNoDefault();

    ProPrototype selectDefaultByCatalog(@Param("catalogid")String catalogid);
}
