package com.ich.proman.message.service;

import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.project.pojo.ProRole;

import java.util.List;

public interface PMessageService {

    public String sendMessageToIds(List<String> userids ,String content,Integer source,String sourceid);

    public String sendMessageToId(String userid ,String content,Integer source,String sourceid);
}
