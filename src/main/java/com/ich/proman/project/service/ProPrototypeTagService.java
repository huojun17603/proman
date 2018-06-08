package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeTag;

import java.util.List;

public interface ProPrototypeTagService {

    public HttpResponse addTag(ProPrototypeTag tag);

    public HttpResponse editIteration(String id,String content,String iterationcauses);

    public HttpResponse editMap(String id,String mapx,String mapy);

    public HttpResponse addTagByImport(String prototypeid,String id);

    public List<ProPrototypeTag> findNormalListByPid(String prototypeid);

    /** 获取历史版本 */
    public List<ProPrototypeTag> findVersions(String id);

    HttpResponse addTagByImports(String prototypeid, String ids);

    HttpResponse findById(String id);

    HttpResponse editToDel(String id);
}
