package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProPrototypeCatalog;

import java.util.List;

public interface ProPrototypeCatalogService {

    HttpResponse addPCatalog(ProPrototypeCatalog pcatalog);

    HttpResponse editPCatalog(ProPrototypeCatalog pcatalog);

    HttpResponse delPCatalog(String id);

    ProPrototypeCatalog findById(String id);

    List<?> findListByFid(String catalogid,String fid);

    List<?> findTree(String catalogid,String fid);
}
