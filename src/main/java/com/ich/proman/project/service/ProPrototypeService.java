package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.proman.project.pojo.ProPrototype;

import java.util.List;

public interface ProPrototypeService {

    HttpResponse addPrototype(ProPrototype prototype);

    /** 仅可以修改标题 */
    HttpResponse editPrototypeTitle(String id,String title);

    /** 一条新的原型记录（理由），原有记录标记为历史，使用全新的标记信息 */
    HttpResponse editPrototypeToImg(String id,String img,String iterationcauses);

    /** 允许创建者标记版本为删除状态，但必须说明删除原因（目的：仅保留有价值的版本给团队浏览） */
    HttpResponse editPrototypeToDel(String id,String deletecauses);

    /** 获取模块下所有的原型列表（用户验证） */
    List<ProPrototype> findListByMid(String mid);

    /** 获取模块下所有当前有效的原型列表 */
    List<ProPrototype> findNormalListByMid(String mid);

    /** 获取模块下所有当前有效的原型列表(总数) */
    Integer findCountNormalListByMid(String mid);

    /** 获取历史版本 */
    List<ProPrototype> findVersions(String id);
}
