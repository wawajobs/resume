package com.resume.service;

import java.util.List;

import com.resume.dto.InterviewFlow;

public interface InterviewFlowService {
	
	public Long insertInterviewFlow(InterviewFlow interviewFlow);
	
	public void updateFlowStatus(InterviewFlow interviewFlow);
	
	public InterviewFlow findById(long id);
	
	public InterviewFlow findByResumeId(long resumeId);
	
	public List<InterviewFlow> list(Long step,String col,String order,Integer page,Integer size);
	
	public InterviewFlow findByResumeIdWithResume(long resumeId);

}
