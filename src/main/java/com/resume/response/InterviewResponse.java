package com.resume.response;

import com.resume.dto.InterviewFlow;

public class InterviewResponse extends BaseResponse{

	private static final long serialVersionUID = 2818978083043276153L;
	
	private InterviewFlow interviewFlow;

	public InterviewFlow getInterviewFlow() {
		return interviewFlow;
	}

	public void setInterviewFlow(InterviewFlow interviewFlow) {
		this.interviewFlow = interviewFlow;
	}
	

}
