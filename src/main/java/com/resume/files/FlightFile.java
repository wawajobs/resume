package com.resume.files;

public class FlightFile extends BaseFile{

	public FlightFile(String fileName) {
		super(fileName);
	}

	@Override
	protected String getModulePath() {
		
		return "/files/resume/flight/";
	}

}
