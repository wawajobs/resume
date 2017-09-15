package com.resume.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resume.common.MailSender;
import com.resume.dto.Feedback;
import com.resume.response.BaseResponse;
import com.resume.response.ResponseModel;
import com.resume.service.FeedbackService;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends AbstractController{

	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private MailSender mailSender;
	
//	private ExecutorService executor = new ThreadPoolExecutor(5);
	
	@ResponseBody
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ResponseModel addFeedback(String name,String email,String title,String message){
		log.info("@ /feedback/add email:{}",new Object[]{email});
		BaseResponse resp = new BaseResponse();
		Feedback feedback = new Feedback();
		feedback.setName(name);
		feedback.setEmail(email);
		feedback.setTitle(title);
		feedback.setMessage(message);
		feedback.setCreateTime(new Date());
		feedbackService.saveFeedback(feedback);
		
		mailSender.sendMail("lishuai3345@126.com", title, message + "\n from:"+email);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public ResponseModel list(Integer page,Integer size){
		log.info("@ /feedback/list page:{},size:{}",new Object[]{page,size});
		BaseResponse resp = new BaseResponse();
		List<Feedback> feedbacks = feedbackService.getFeedback(page, size);
		resp.setData(feedbacks);
		return resp;
		
	}
}
