package com.resume.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resume.dao.UserDao;
import com.resume.dto.UserInfo;
import com.resume.po.UserInfoPo;
import com.resume.service.UserService;
import com.resume.service.exception.UserException;
import com.resume.service.exception.UserException.UserExceptionType;
import com.resume.util.BeanUtil;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public UserInfo queryByUsername(String username) {
		UserInfoPo userInfoPo = userDao.queryByUsername(username);
		UserInfo userInfo = BeanUtil.createCopy(userInfoPo, UserInfo.class);
		if(null != userInfo && null != userInfoPo){
			userInfo.setEnabled("0".equals(userInfoPo.getLocked()));
			return userInfo;
		}
		return null;
	}
	
	@Override
	public List<UserInfo> queryByIds(List<Long> ids) {
		if(null == ids || ids.isEmpty()){
			return null;
		}
		List<UserInfoPo> users = userDao.queryByIds(ids);
		return BeanUtil.createCopyList(users, UserInfo.class);
	}

	@Override
	@Transactional
	public void register(String email, String password,String role) throws UserException{
		UserInfoPo user = userDao.queryByUsername(email);
		if(null != user){
			throw new UserException(UserExceptionType.USER_EXISTS);
		}
		user = new UserInfoPo();
		user.setEmail(email);
		user.setPassword(password);
		user.setLocked("0");
		userDao.insertUser(user);
		
		
	}

	@Override
	public void resetPassword(String email, String password) {
		userDao.resetPasswordByEmail(password, email);
	}
	
	@Override
	public List<UserInfo> listUsers() {

		List<UserInfo> userInfos = new ArrayList<UserInfo>();
		List<UserInfoPo> userPos = userDao.listUsers();
		for (UserInfoPo userInfoPo : userPos) {
			
			UserInfo userInfo = BeanUtil.createCopy(userInfoPo, UserInfo.class);
			userInfo.setEnabled("0".equals(userInfoPo.getLocked()));
			userInfos.add(userInfo);
		}
		
		return userInfos;
	}

	@Override
	public void setEmployee(String email) {

		userDao.setEmployee(email);
	}
	
	@Override
	public void deleteUser(Long userId){
		userDao.deleteUser(userId);
	}
	
	@Override
	public void lockUser(Long userId,String locked){
		UserInfoPo po = new UserInfoPo();
		po.setId(userId);
		po.setLocked(locked);
		userDao.updateUserStatus(po);
	}

}
