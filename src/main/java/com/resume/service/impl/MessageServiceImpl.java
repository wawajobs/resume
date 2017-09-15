package com.resume.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dao.MessageDao;
import com.resume.dto.Feedback;
import com.resume.dto.Message;
import com.resume.po.FeedbackPo;
import com.resume.po.MessagePo;
import com.resume.service.MessageService;
import com.resume.util.BeanUtil;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao messageDao;
	
	@Override
	public Long saveMessage(Message message) {
		MessagePo messagePo = BeanUtil.createCopy(message, MessagePo.class);
		messageDao.insertMessage(messagePo);
		return messagePo.getId();
	}

	@Override
	public List<Message> getMessages(Integer page, Integer size) {
		Integer beginIndex = null;
		if(null != page && null != size){
			beginIndex = (page - 1)*size;
		}
		List<MessagePo> messagePos = messageDao.queryList(beginIndex, size);
		return BeanUtil.createCopyList(messagePos, Message.class);
	}

}
