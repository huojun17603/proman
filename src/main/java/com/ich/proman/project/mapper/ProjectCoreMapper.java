package com.ich.proman.project.mapper;

import com.ich.proman.project.pojo.Project;

public interface ProjectCoreMapper {
    Project selectById(String projectid);
}
