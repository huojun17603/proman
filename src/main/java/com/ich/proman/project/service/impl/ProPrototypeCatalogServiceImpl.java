package com.ich.proman.project.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.EasyUITreeNode;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.Constant;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.dto.PCatalogTreeNode;
import com.ich.proman.project.mapper.ProPrototypeCatalogMapper;
import com.ich.proman.project.mapper.ProPrototypeMapper;
import com.ich.proman.project.mapper.ProPrototypeTagMapper;
import com.ich.proman.project.mapper.ProjectCoreMapper;
import com.ich.proman.project.pojo.ProPrototype;
import com.ich.proman.project.pojo.ProPrototypeCatalog;
import com.ich.proman.project.pojo.ProPrototypeTag;
import com.ich.proman.project.pojo.Project;
import com.ich.proman.project.service.ProPrototypeCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProPrototypeCatalogServiceImpl implements ProPrototypeCatalogService {

    @Autowired
    private ProPrototypeMapper proPrototypeMapper;
    @Autowired
    private ProPrototypeTagMapper prototypeTagMapper;
    @Autowired
    private ProPrototypeCatalogMapper prototypeCatalogMapper;
    @Autowired
    private ProjectCoreMapper projectCoreMapper;

    @Override
    public HttpResponse addPCatalog(ProPrototypeCatalog pcatalog) {
        if(ObjectHelper.isEmpty(pcatalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(pcatalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(pcatalog.getTitle())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入目录标题！");
        if(ObjectHelper.isEmpty(pcatalog.getCatalogid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择项目目录！");
        if(ObjectHelper.isNotEmpty(pcatalog.getPrototypeid())){
            ProPrototype prototype = proPrototypeMapper.selectById(pcatalog.getPrototypeid());
            if(ObjectHelper.isEmpty(prototype)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的原型页！");
            pcatalog.setPrototypename(prototype.getTitle());
        }
        ProPrototypeCatalog entity = prototypeCatalogMapper.selectByPTitle(pcatalog.getCatalogid(),pcatalog.getTitle());
        if(ObjectHelper.isNotEmpty(entity)) return new HttpResponse(HttpResponse.HTTP_ERROR,"重复的目录标题！");
        pcatalog.setId(IDUtils.createUUId());
        prototypeCatalogMapper.insert(pcatalog);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse editPCatalog(ProPrototypeCatalog pcatalog) {
        ProPrototypeCatalog catalog = this.prototypeCatalogMapper.selectById(pcatalog.getId());
        if(ObjectHelper.isEmpty(catalog)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息！");
        if(ObjectHelper.isEmpty(catalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(catalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        if(ObjectHelper.isEmpty(pcatalog.getTitle())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请输入目录标题！");
        ProPrototypeCatalog entity = prototypeCatalogMapper.selectByPTitle(catalog.getProjectid(),pcatalog.getTitle());
        if(ObjectHelper.isNotEmpty(entity)&&!catalog.getId().equals(entity.getId())) {
            return new HttpResponse(HttpResponse.HTTP_ERROR, "重复的目录标题！");
        }
//        if(ObjectHelper.isEmpty(pcatalog.getPrototypeid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"请选择原型页！");
//        ProPrototype prototype = proPrototypeMapper.selectById(pcatalog.getPrototypeid());
//        if(ObjectHelper.isEmpty(prototype)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的原型页！");
//        pcatalog.setPrototypename(prototype.getTitle());

        if(ObjectHelper.isNotEmpty(pcatalog.getPrototypeid())){
            ProPrototype prototype = proPrototypeMapper.selectById(pcatalog.getPrototypeid());
            if(ObjectHelper.isEmpty(prototype)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的原型页！");
            pcatalog.setPrototypename(prototype.getTitle());
        }
        prototypeCatalogMapper.update(pcatalog);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public HttpResponse delPCatalog(String id) {
        ProPrototypeCatalog catalog = this.prototypeCatalogMapper.selectById(id);
        if(ObjectHelper.isEmpty(catalog)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的目录信息！");
        if(ObjectHelper.isEmpty(catalog.getProjectid())) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        Project project = projectCoreMapper.selectById(catalog.getProjectid());
        if(ObjectHelper.isEmpty(project)||project.getStatus()!= Constant.STATUS_NORMAL) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的项目信息！");
        List<ProPrototypeCatalog> list = findListByFid(catalog.getCatalogid(),catalog.getId());
        if(ObjectHelper.isNotEmpty(list)) return new HttpResponse(HttpResponse.HTTP_ERROR,"存在下级目录，不可删除！");
        prototypeCatalogMapper.delete(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }


    @Override
    public ProPrototypeCatalog findById(String id) {
        return this.prototypeCatalogMapper.selectById(id);
    }

    @Override
    public List<ProPrototypeCatalog> findListByFid(String catalogid,String fid) {
        if(ObjectHelper.isEmpty(fid)){
            return this.prototypeCatalogMapper.selectRootList(catalogid);
        }else {
            return this.prototypeCatalogMapper.selectListByFid(catalogid,fid);
        }
    }

    @Override
    public List<?> findTree(String catalogid,String fid) {
        List<PCatalogTreeNode> nodes = new ArrayList<>();
        List<ProPrototypeCatalog> list = findListByFid(catalogid,fid);
        if(ObjectHelper.isEmpty(list)){
            return null;
        }else{
            for(ProPrototypeCatalog catalog : list){
                PCatalogTreeNode node = new PCatalogTreeNode();
                node.setId(catalog.getId());
                node.setText(catalog.getTitle());
                node.setState("open");
                node.setParent_id(catalog.getFid());
                node.setChildren(findTree(catalogid,catalog.getId()));
                List<String> attr = new ArrayList<>();
                attr.add(catalog.getPrototypeid());
                node.setAttributes(attr);
                node.setPrototypeid(catalog.getPrototypeid());
                node.setPrototypename(catalog.getPrototypename());
                node.setTitle(catalog.getTitle());
                node.setRemark(catalog.getRemark());
                if(ObjectHelper.isEmpty(node.getChildren())){

                }
                nodes.add(node);
            }
            return nodes;
        }
    }
}
