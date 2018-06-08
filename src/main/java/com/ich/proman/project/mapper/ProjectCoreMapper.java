package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.Project;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProjectCoreMapper {

    void insert(Project project);

    void updateTitle(@Param("id") String id,@Param("title") String title);

    void updateProjectToDel(@Param("id") String id,@Param("deletecauses") String deletecauses);

    void updateProjectToHis(@Param("id") String id,@Param("iterationcauses") String iterationcauses);

    Project selectById(@Param("id") String id);

    Project selectByTitleAndVersion(@Param("title") String title,@Param("version") String version);

    Project selectByVersion(@Param("id") String id,@Param("version") String version);

    List<Project> selectGroupByVersionid();

    List<Project> selectVersionById(@Param("id") String id);

    List<Map<String,Object>> selectModularListByPid(@Param("id") String id);

    List<Map<String,Object>> selectModularListByQuery(Map<String, Object> paramMap);
}
