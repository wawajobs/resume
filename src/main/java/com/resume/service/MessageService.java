package com.resume.service;

import java.util.List;

import com.resume.dto.Message;

public interface MessageService {
	
	Long saveMessage(Message message);
	
	List<Message> getMessages(Integer page,Integer size);

}
