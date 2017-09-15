package com.resume.files;

public class PhotoFile extends BaseFile{

	public PhotoFile(String fileName) {
		super(fileName);
	}

	@Override
	protected String getModulePath() {
		
		return "/files/resume/photo/";
	}

}
