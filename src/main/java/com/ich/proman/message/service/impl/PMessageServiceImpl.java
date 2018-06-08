package com.ich.proman.message.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ich.admin.dto.LocalEmployee;
import com.ich.admin.service.impl.LocalEmployeeServiceImpl;
import com.ich.core.base.IDUtils;
import com.ich.core.base.ObjectHelper;
import com.ich.core.http.entity.HttpResponse;
import com.ich.core.http.entity.PageView;
import com.ich.proman.message.mapper.PMessageMapper;
import com.ich.proman.message.pojo.PMessage;
import com.ich.proman.message.service.PMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PMessageServiceImpl implements PMessageService {

    @Autowired
    private PMessageMapper messageMapper;
    @Autowired
    private LocalEmployeeServiceImpl localEmployeeServiceImpl;

    @Override
    public void sendMessageToIds(List<String> userids, String content, Integer source, String sourceid) {
        for (String userid : userids){
            sendMessageToId(userid,content,source,sourceid);
        }
    }

    @Override
    public void sendMessageToId(String userid, String content, Integer source, String sourceid) {
        PMessage message = new PMessage();
        message.setId(IDUtils.createUUId());
        message.setUserid(userid);
        message.setCreatetime(new Date());
        message.setStatus(PMessage.UNREAD);
        message.setContent(content);
        message.setSource(source);
        message.setSourceid(sourceid);
        messageMapper.insert(message);
    }

    @Override
    public HttpResponse editToConfirm(String id) {
        if(ObjectHelper.isEmpty(id)) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的消息！");
        PMessage message = messageMapper.selectById(id);
        if(ObjectHelper.isEmpty(message)||message.getStatus()!= PMessage.UNREAD) return new HttpResponse(HttpResponse.HTTP_ERROR,"无效的消息！");
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        if(!message.getUserid().equals(employee.getEmployeeId()))
            return new HttpResponse(HttpResponse.HTTP_ERROR,"用户错误！");
        messageMapper.updateToConfirm(id);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK);
    }

    @Override
    public List<PMessage> findAllList(PageView view) {
        PageHelper.startPage(view.getPage(),view.getRows());
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        List<PMessage> list = messageMapper.selectAllList(employee.getEmployeeId());
        PageInfo<PMessage> pageInfo = new PageInfo<>(list);
        view.setRowCount(pageInfo.getTotal());
        return list;
    }

    @Override
    public List<PMessage> findUnreadList() {
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        return messageMapper.selectUnreadList(employee.getEmployeeId());
    }

    @Override
    public HttpResponse findUnreadNum() {
        LocalEmployee employee = localEmployeeServiceImpl.findLocalEmployee();
        Integer unreadNum = messageMapper.selectUnReadNum(employee.getEmployeeId());
        Map<String,Object> data = new HashMap<>();
        data.put("unreadNum",unreadNum);
        return new HttpResponse(HttpResponse.HTTP_OK,HttpResponse.HTTP_MSG_OK,data);
    }

}
