package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProFile;
import com.ich.proman.project.pojo.ProPrototype;

import java.util.List;

public interface ProFileService {

    HttpResponse addFile(ProFile file);

    HttpResponse editFile(String id,String file,String iterationcauses);

    HttpResponse delFile(String id,String deletecauses);

    /** 获取模块下所有的文件列表（验证） */
    List<ProFile> findListByMid(String modularid);

    /** 获取模块下所有当前有效的文件列表 */
    List<ProFile> findNormalListByMid(String modularid);

    /** 获取模块下所有当前有效的文件列表(总数) */
    Integer findCountNormalListByMid(String modularid);

    /** 获取项目下所有当前有效的文件列表 */
    List<ProFile> findNormalListByPid(String projectid);

    /** 获取项目下所有当前有效的文件列表(总数) */
    Integer findCountNormalListByPid(String projectid);

    /** 获取来源下所有当前有效的文件列表 */
    List<ProFile> findNormalListBySource(String projectid,String source,String sourceid);

    /** 获取来源下所有当前有效的文件列表(总数) */
    Integer findCountNormalListBySource(String projectid,String source,String sourceid);

    /** 获取历史版本 */
    List<ProFile> findVersions(String id);
}
