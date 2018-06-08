package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProCatalog;
import com.ich.proman.project.pojo.ProPrototypeCatalog;

import java.util.List;

public interface ProCatalogService {

    List<?> findListByQuery(PageView view, ProjectQuery query);

    HttpResponse addCatalog(ProCatalog catalog);

    HttpResponse editCatalog(ProCatalog catalog);

    HttpResponse addCopyCatalog(String id, String title);

    ProCatalog findById(String id);

    List<?> findAllList(ProjectQuery query);
}
