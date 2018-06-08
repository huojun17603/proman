package com.ich.proman.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.base.TimeUtil;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.Constant;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.mapper.*;
import com.ich.proman.project.pojo.*;
import com.ich.proman.project.service.ProCatalogService;
import com.ich.proman.project.service.ProCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ProCatalogServiceImpl implements ProCatalogService {

    @Autowired
    private ProPrototypeMapper proPrototypeMapper;
    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;
    @Autowired
    private ProCatalogMapper catalogMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;

    @Override
    public List<?> findListByQuery(PageView view, ProjectQuery query) {
        PageHelper.startPage(view.getPage(),view.getRows());
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        List<Map<String,Object>> list = catalogMapper.selectListByQuery(paramMap);
        PageInfo<Map<String,Object>> pageInfo = new PageInfo<Map<String,Object>>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public HttpResponse addCatalog(ProCatalog catalog) {
        if(ObjectHelper.isEmpty(catalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(catalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(catalog.getTitle())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入目录标题！");
        ProCatalog entity = catalogMapper.selectByPTitle(catalog.getProjectid(),catalog.getTitle());
        if(ObjectHelper.isNotEmpty(entity)) return new HttpResponse(HttpResponse.HTTP_ERROR,"重复的目录标题！");
        catalog.setId(IDUtils.createUUId());
        catalogMapper.insert(catalog);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editCatalog(ProCatalog ncatalog) {
        ProCatalog catalog = this.catalogMapper.selectById(ncatalog.getId());
        if(ObjectHelper.isEmpty(catalog)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息！");
        if(ObjectHelper.isEmpty(catalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(catalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(ncatalog.getTitle())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入目录标题！");
        ProCatalog entity = catalogMapper.selectByPTitle(catalog.getProjectid(),ncatalog.getTitle());
        if (ObjectHelper.isNotEmpty(entity)) {
            if (!catalog.getId().equals(entity.getId())) {
                return new HttpResponse(HttpResponse.HTTP_ERROR, "重复的目录标题！");
            }
        }
        catalogMapper.update(ncatalog);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse addCopyCatalog(String id, String title) {
        ProCatalog catalog = catalogMapper.selectById(id);
        if(ObjectHelper.isEmpty(catalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(catalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        ProCatalog entity = catalogMapper.selectByPTitle(catalog.getProjectid(),title);
        if(ObjectHelper.isNotEmpty(entity)) return new HttpResponse(HttpResponse.HTTP_ERROR,"重复的目录标题！");
        //开始复制
        entity = new ProCatalog();
        entity.setId(IDUtils.createUUId());
        entity.setProjectid(catalog.getProjectid());
        entity.setTitle(title);
        catalogMapper.insert(entity);
        List<ProPrototype> prototypes = proPrototypeMapper.selectNormalListByPCid(id);
        for(ProPrototype prototype : prototypes){
            List<ProPrototype> vprototypes = proPrototypeMapper.selectVersions(prototype.getGroupid());
            String vprototype_groupid = IDUtils.createUUId();
            for(ProPrototype vprototype : vprototypes){
                List<ProPrototypeTag> tags = prototypeTagMapper.selectNormalListByPid(vprototype.getId());
                vprototype.setId(IDUtils.createUUId());
                vprototype.setCatalogid(entity.getId());
                vprototype.setGroupid(vprototype_groupid);
                for(ProPrototypeTag tag : tags){
                    List<ProPrototypeTag> vtags = prototypeTagMapper.selectVersions(tag.getGroupid());
                    String vtag_groupid = IDUtils.createUUId();
                    for(ProPrototypeTag vtag : vtags){
                        vtag.setId(IDUtils.createUUId());
                        vtag.setPrototypeid(vprototype.getId());
                        vtag.setGroupid(vtag_groupid);
                        prototypeTagMapper.insert(vtag);
                    }
                }
                proPrototypeMapper.insert(vprototype);
            }
        }
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public ProCatalog findById(String id) {
        return this.catalogMapper.selectById(id);
    }

    @Override
    public List<?> findAllList(ProjectQuery query) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        if(ObjectHelper.isNotEmpty(query.getSearchkey()))paramMap.put("searchkey", query.getSearchkey());
        if(ObjectHelper.isNotEmpty(query.getProjectid()))paramMap.put("projectid", query.getProjectid());
        List<Map<String,Object>> list = catalogMapper.selectListByQuery(paramMap);
        return list;
    }

}
