package com.resume.response;


public class LoginResponse extends BaseResponse {

	private static final long serialVersionUID = -2868856456782053607L;

	private String username;
	
	private String password;
	
	private String skip;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSkip() {
		return skip;
	}

	public void setSkip(String skip) {
		this.skip = skip;
	}
	
	
	

}
