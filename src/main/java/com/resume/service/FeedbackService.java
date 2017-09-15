package com.resume.service;

import java.util.List;

import com.resume.dto.Feedback;

public interface FeedbackService {
	
	Long saveFeedback(Feedback feedback);
	
	List<Feedback> getFeedback(Integer page,Integer size);

}
