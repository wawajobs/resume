package com.resume.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resume.dto.Message;
import com.resume.response.BaseResponse;
import com.resume.response.ResponseModel;
import com.resume.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController{

	@Autowired
	private MessageService messageService;
	
	@ResponseBody
	@RequestMapping(value="/send",method=RequestMethod.POST)
	public ResponseModel addMessage(Message message){
		log.info("@ /message/send message:{}",new Object[]{message});
		BaseResponse resp = new BaseResponse();
		messageService.saveMessage(message);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public ResponseModel list(Integer page,Integer size){
		log.info("@ /message/list page:{},size:{}",new Object[]{page,size});
		BaseResponse resp = new BaseResponse();
		List<Message> messages = messageService.getMessages(page, size);
		resp.setData(messages);
		return resp;
		
	}
}
