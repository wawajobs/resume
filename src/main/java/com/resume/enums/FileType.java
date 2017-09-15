package com.resume.enums;

public enum FileType {
	
	RESUME_DOC(0,"简历"),
	INTRODUCTION_VIDEO(1,"介绍视频"),
	PHOTO(2,"照片"),
	FLIGHT_TICKET(3,"机票"),
	CERTIFICATION(4,"证书");
	
	private int code;
	
	private String desc;
	
	private FileType(int code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	

}
