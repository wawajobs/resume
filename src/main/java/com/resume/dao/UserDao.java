package com.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resume.po.UserInfoPo;

public interface UserDao {
	
	/**
	 * 通过用户名查询用户信息
	 * @param username 用户名（用户名，手机号，邮箱）
	 * @return
	 */
	UserInfoPo queryByUsername(String username);
	
	List<UserInfoPo> queryByIds(@Param("ids")List<Long> ids);
	
	/**
	 * 注册用户信息
	 * @param userInfoPo
	 */
	void insertUser(UserInfoPo userInfoPo);
	
	/**
	 * 重置密码
	 * @param password
	 * @param email
	 */
	void resetPasswordByEmail(@Param("password")String password,@Param("email")String email);
	
	List<UserInfoPo> listUsers();
	
	void updateUserStatus(UserInfoPo userInfoPo);
	
	void deleteUser(long id);
	
	void setEmployee(String email);
	

}
