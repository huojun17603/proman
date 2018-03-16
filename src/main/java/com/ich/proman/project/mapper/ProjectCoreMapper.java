package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.Project;
import org.apache.ibatis.annotations.Param;

public interface ProjectCoreMapper {

    void insert(Project project);

    void updateTitle(@Param("id") String id,@Param("title") String title);

    void updateProjectToDel(@Param("id") String id,@Param("deletecauses") String deletecauses);

    void updateProjectToHis(@Param("id") String id,@Param("iterationcauses") String iterationcauses);

    Project selectById(@Param("id") String id);

    Project selectByTitleAndVersion(@Param("title") String title,@Param("version") String version);

    Project selectByVersion(@Param("id") String id,@Param("version") String version);

}
