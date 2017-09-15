package com.resume.response;

import com.resume.spring.security.User;

public class UserResponse extends BaseResponse{

	private static final long serialVersionUID = -8335211545963968485L;
	
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
