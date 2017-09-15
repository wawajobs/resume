package com.resume.enums;

public enum BaseRoleType {
	
	MEMEBER("MEMEBER","普通成员"),
	EMPLOYEE("EMPLOYEE","工作人员"),
	ADMIN("ADMIN","管理员");
	
	private String code;
	
	private String desc;
	
	private BaseRoleType(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public static BaseRoleType parseType(String code){
		BaseRoleType[] values = values();
		for (BaseRoleType baseRoleType : values) {
			if(baseRoleType.code.equals(code)){
				return baseRoleType;
			}
		}
		return null;
	}
	
	

}
