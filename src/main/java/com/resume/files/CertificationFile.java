package com.resume.files;

public class CertificationFile extends BaseFile{

	public CertificationFile(String fileName) {
		super(fileName);
	}

	@Override
	protected String getModulePath() {
		
		return "/files/resume/certification/";
	}

}
