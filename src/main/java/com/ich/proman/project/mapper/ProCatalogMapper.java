package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProCatalog;
import com.ich.proman.project.pojo.ProPrototypeCatalog;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProCatalogMapper {

    void insert(ProCatalog catalog);

    void update(ProCatalog catalog);

    ProCatalog selectById(@Param("id") String id);

    ProCatalog selectByPTitle(@Param("projectid") String projectid, @Param("title") String title);

    List<Map<String,Object>> selectListByQuery(Map<String, Object> paramMap);



}
