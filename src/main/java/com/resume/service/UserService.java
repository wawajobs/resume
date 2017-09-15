package com.resume.service;

import java.util.List;

import com.resume.dto.UserInfo;
import com.resume.service.exception.UserException;

public interface UserService {
	
	/**
	 * @param username
	 * @return
	 */
	UserInfo queryByUsername(String username);
	/**
	 * @param username
	 * @return
	 */
	List<UserInfo> queryByIds(List<Long> ids);
	
	/**
	 * 注册用户
	 * @param email
	 * @param password
	 */
	void register(String email,String password,String role) throws UserException;
	
	/**
	 * 重置密码
	 * @param email
	 * @param password
	 */
	void resetPassword(String email,String password);
	
	List<UserInfo> listUsers();

	void setEmployee(String email);
	
	public void deleteUser(Long userId);
	
	public void lockUser(Long userId,String locked);

}
