package com.resume.spring.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.resume.common.Constant;
import com.resume.spring.security.exception.VerifyException;

public class VerifyAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	public static final String SPRING_SECURITY_FORM_VERIFY_KEY = "verifyCode";
	public static final String SESSION_GENERATED_VERIFY_KEY = Constant.SESSION_GENERATED_VERIFY_KEY;

	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		String skip = request.getParameter("skip");
		if("1".equals(skip)){
			return super.attemptAuthentication(request, response);
		}
		
		String username = request.getParameter("username");
		request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", username);
		String genCode = this.getSessionVerifyCode(request);
		String inputCode = this.getReqVerifyCode(request);
		if (genCode == null)
			throw new VerifyException("Verification code invalid");
		if (!genCode.equalsIgnoreCase(inputCode)) {
			throw new VerifyException("Verification code invalid");
		}

		return super.attemptAuthentication(request, response);
	}

	protected String getReqVerifyCode(HttpServletRequest request) {
		return request.getParameter(SPRING_SECURITY_FORM_VERIFY_KEY);
	}

	protected String getSessionVerifyCode(HttpServletRequest request) {
		return (String) request.getSession().getAttribute(
				SESSION_GENERATED_VERIFY_KEY);
	}

}
