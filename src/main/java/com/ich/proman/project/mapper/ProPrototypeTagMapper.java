package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProPrototypeTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProPrototypeTagMapper {

    void insert(ProPrototypeTag tag);

    void updateToHis(@Param("id") String id);

    void updateMap(@Param("id") String id, @Param("mapx") String mapx,@Param("mapy")  String mapy);

    ProPrototypeTag selectById(@Param("id") String id);

    ProPrototypeTag selectNormalByCode(@Param("prototypeid") String prototypeid,@Param("code") String code);

    List<ProPrototypeTag> selectNormalListByPid(@Param("prototypeid") String prototypeid);

    List<ProPrototypeTag> selectVersions(@Param("id") String id);
}
