package com.resume.spring.security;

import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

@SuppressWarnings("deprecation")
public class StandardPasswordEncoder extends Md5PasswordEncoder implements PasswordEncoder {
	
	private static final String SALT = "!@KMSXEDU29121jh@*&$";
	
	public String encodePassword(String rawPass){
		return encodePassword(rawPass, SALT);
	}
	
	@Override
	public String encodePassword(String rawPass, Object salt)
			throws DataAccessException {
		return super.encodePassword(rawPass, SALT);
	}

	@Override
	public boolean isPasswordValid(String encPass, String rawPass, Object salt)
			throws DataAccessException {
		return super.isPasswordValid(encPass, rawPass, SALT);
	}
	
	public static void main(String[] args) {
		System.out.println(new StandardPasswordEncoder().encodePassword("L0nd0n17", SALT));
	}
	
}
