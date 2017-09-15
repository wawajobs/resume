package com.resume.util;

import java.io.InputStream;

public class MailAffix {
	

	private InputStream affixInput = null; // 附件输入流
	private String affixName = ""; // 附件名称
	
	private MailAffix(InputStream affixInput, String affixName) {
		super();
		this.affixInput = affixInput;
		this.affixName = affixName;
	}
	
	
	public MailAffix getAffixInstance(InputStream affixInput, String affixName){
		return new MailAffix(affixInput, affixName);
	}
	
	public InputStream getAffixInput() {
		return affixInput;
	}
	public void setAffixInput(InputStream affixInput) {
		this.affixInput = affixInput;
	}
	public String getAffixName() {
		return affixName;
	}
	public void setAffixName(String affixName) {
		this.affixName = affixName;
	}
	
	


}
