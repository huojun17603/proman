package com.ich.proman.message.service;

import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.message.pojo.PMessage;

import java.util.List;

public interface PMessageService {

    public void sendMessageToIds(List<String> userids ,String content,Integer source,String sourceid);

    public void sendMessageToId(String userid ,String content,Integer source,String sourceid);

    public HttpResponse editToConfirm(String id);

    public List<PMessage> findAllList(PageView view);

    public List<PMessage> findUnreadList();

    public HttpResponse findUnreadNum();

}
