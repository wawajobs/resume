package com.resume.files;

public class ResumeFile extends BaseFile{

	public ResumeFile(String fileName) {
		super(fileName);
	}

	@Override
	protected String getModulePath() {
		
		return "/files/resume/doc/";
	}

}
