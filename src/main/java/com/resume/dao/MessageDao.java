package com.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resume.po.MessagePo;

public interface MessageDao {

	Long insertMessage(MessagePo messagePo);
	
	List<MessagePo> queryList(@Param("beginIndex")Integer beginIndex,@Param("size")Integer size);
}
