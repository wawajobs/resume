package com.resume.files;

public class IntroductionVideoFile extends BaseFile{

	public IntroductionVideoFile(String fileName) {
		super(fileName);
	}

	@Override
	protected String getModulePath() {
		
		return "/files/resume/introduction/video/";
	}

}
