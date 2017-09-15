package com.resume.util;

public class MailUser {
	
	private String host = ""; // smtp服务器
	private String user = ""; // 用户名
	private String pwd = "";
	
	private MailUser(String host,String user,String pwd){
		this.host = host;
		this.user = user;
		this.pwd = pwd;
	}
	
	public static MailUser getInstance(String host,String user,String pwd){
		return new MailUser(host, user, pwd);
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
