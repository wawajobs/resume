package com.resume.common;

import com.resume.util.MailUser;
import com.resume.util.MailUtil;

public class MailSender {
	
	private String host = ""; // smtp服务器
	private String user = ""; // 用户名
	private String pwd = "";
	
	public void sendMail( String to, String title, String content){
		
		MailUtil.send(MailUser.getInstance(host, user, pwd), to, title, content);
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
	

}
