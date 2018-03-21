package com.ich.proman.project.service;

import com.ich.proman.project.pojo.ProBug;

import java.util.List;

public interface ProBugService {
    List<ProBug> findListByMid(String id);

    //新增：由测试人员从外部收集资料后，提出BUG（业务、数据、视图、错误）

    //确认：由开发人员确认，即表示BUG是真实存在的

    //修复：由开发人员完成修复后告知测试人员

    //
}
