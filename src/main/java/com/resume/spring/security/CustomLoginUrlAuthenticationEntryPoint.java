package com.resume.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class CustomLoginUrlAuthenticationEntryPoint extends
		LoginUrlAuthenticationEntryPoint {
	
	public CustomLoginUrlAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}
	
	private static final String AJAX_REQUEST_HEAD_FLAG = "XMLHttpRequest";

	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		if ((isAjaxRequest(request)) && (authException != null)) {
			response.sendError(401);
		} else {
			super.commence(request, response, authException);
		}
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		return AJAX_REQUEST_HEAD_FLAG.equalsIgnoreCase(request
				.getHeader("X-Requested-With"));
	}
}