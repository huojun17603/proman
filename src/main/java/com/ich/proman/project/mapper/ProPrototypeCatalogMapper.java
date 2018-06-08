package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProPrototypeCatalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProPrototypeCatalogMapper {

    void insert(ProPrototypeCatalog pcatalog);

    void update(ProPrototypeCatalog pcatalog);

    ProPrototypeCatalog selectById(@Param("id") String id);

    ProPrototypeCatalog selectByPTitle(@Param("catalogid")String catalogid, @Param("title")String title);

    void delete(@Param("id")String id);

    List<ProPrototypeCatalog> selectRootList(@Param("catalogid")String catalogid);

    List<ProPrototypeCatalog> selectListByFid(@Param("catalogid")String catalogid, @Param("fid")String fid);
}
