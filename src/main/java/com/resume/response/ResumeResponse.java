package com.resume.response;

import com.resume.dto.ResumeInfo;

public class ResumeResponse extends BaseResponse{

	private static final long serialVersionUID = 2818978083043276153L;
	
	private ResumeInfo resumeInfo;

	public ResumeInfo getResumeInfo() {
		return resumeInfo;
	}

	public void setResumeInfo(ResumeInfo resumeInfo) {
		this.resumeInfo = resumeInfo;
	}
	
	

}
