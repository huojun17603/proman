package com.ich.proman.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import com.ich.proman.project.pojo.ProRole;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PMessageServiceImpl implements PMessageService {

    @Override
    public String sendMessageToIds(List<String> userids, String content, Integer source, String sourceid) {
        return null;
    }

    @Override
    public String sendMessageToId(String userid, String content, Integer source, String sourceid) {
        return null;
    }
}
