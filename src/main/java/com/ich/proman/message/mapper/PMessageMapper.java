package com.ich.proman.message.mapper;

import com.ich.proman.message.pojo.PMessage;
import org.apache.ibatis.annotations.Param;

public interface PMessageMapper {

    public void insert(PMessage message);

    public void updateToConfirm(@Param("id") String id);

    public PMessage selectById(@Param("id") String id);

    public Integer selectUnReadNum(String userid);
}
