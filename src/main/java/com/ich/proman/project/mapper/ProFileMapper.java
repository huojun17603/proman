package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.ProFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProFileMapper {

    void insert(ProFile file);

    void updateToHis(@Param("id") String id);

    void updateToDis(@Param("id") String id, @Param("deletecauses") String deletecauses);

    List<ProFile> selectByName(@Param("projectid") String projectid, @Param("filename") String filename);

    ProFile selectById(@Param("id") String id);

    List<ProFile> selectListByMid(@Param("modularid") String modularid);

    List<ProFile> selectNormalListByMid(@Param("modularid") String modularid);

    Integer selectCountNormalListByMid(@Param("modularid") String modularid);

    List<ProFile> selectNormalListByPid(@Param("projectid") String projectid);

    Integer selectCountNormalListByPid(@Param("projectid") String projectid);

    List<ProFile> selectNormalListBySource(@Param("projectid") String projectid,@Param("source") String source, @Param("sourceid") String sourceid);

    Integer selectCountNormalListBySource(@Param("projectid") String projectid,@Param("source") String source, @Param("sourceid") String sourceid);

    List<ProFile> selectVersions(@Param("id") String id);
}
