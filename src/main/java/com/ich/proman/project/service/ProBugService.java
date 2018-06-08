package com.ich.proman.project.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.base.ProjectQuery;
import com.ich.proman.project.pojo.ProBug;
import com.ich.proman.project.pojo.ProTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ProBugService {

    /** 新增 */
    HttpResponse addBUG(ProBug bug);
    /** 开启 */
    HttpResponse editToOpen(ProBug bug);
    /** 测试 */
    HttpResponse editToTest(String id,String repairremark);
    /** 完成 */
    HttpResponse editToCom(String id);
    /** 重新开启 */
    HttpResponse editToReopen(String id);
    /** 关闭 */
    HttpResponse editToColse(String id,String repairremark);

    /** 指派BUG */
    HttpResponse editBUGToAppoint(String id,String userid,String username);
    /** 领取BUG */
    HttpResponse editBUGToReceive(String id);

    ProBug findById(String id);

    List<Map<String,Object>> findListByQuery(PageView view, ProjectQuery query);

    List<ProBug> findListByMid(String modularid);

}
