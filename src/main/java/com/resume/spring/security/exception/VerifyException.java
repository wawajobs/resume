package com.resume.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

public class VerifyException extends AuthenticationException{

	private static final long serialVersionUID = -9198847258227310129L;

	public VerifyException(String msg) {
		super(msg);
	}

}
