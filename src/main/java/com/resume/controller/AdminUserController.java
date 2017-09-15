package com.resume.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resume.dto.ResumeInfo;
import com.resume.dto.UserInfo;
import com.resume.enums.BaseRoleType;
import com.resume.response.BaseResponse;
import com.resume.response.ResponseModel;
import com.resume.response.UserResponse;
import com.resume.service.ResumeService;
import com.resume.service.UserService;
import com.resume.spring.security.SecurityContextUtil;
import com.resume.spring.security.User;

@Controller
@RequestMapping("user")
public class AdminUserController extends AbstractController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ResumeService resumeService;
	
	@RequestMapping("/page")
	public String showListUser(){
		User user = (User)SecurityContextUtil.getUserDetails();
		if(BaseRoleType.EMPLOYEE.getCode().equals(user.getRole()) ||
				BaseRoleType.ADMIN.getCode().equals(user.getRole())){
			
			return "/manage/userList";
		}
		
		return "redirect:/interview/page";
	}
	
	@ResponseBody
	@RequestMapping("/findMenu")
	public ResponseModel findMenu(){
		log.info("@ findMenu");
		BaseResponse resp = new BaseResponse();
		User user = (User)SecurityContextUtil.getUserDetails();
		List<Map<String, String>> menus = new ArrayList<Map<String,String>>();
		if(BaseRoleType.ADMIN.getCode().equals(user.getRole())){
			Map<String, String> resumeMenuMap = new HashMap<String, String>();
			resumeMenuMap.put("name", "Management");
			resumeMenuMap.put("value", "/interview/page");
			menus.add(resumeMenuMap);
			Map<String, String> userMenuMap = new HashMap<String, String>();
			userMenuMap.put("name", "Administration");
			userMenuMap.put("value", "/user/page");
			menus.add(userMenuMap);
			
			
			
		}else if(BaseRoleType.EMPLOYEE.getCode().equals(user.getRole())){
			ResumeInfo resumeInfo = resumeService.getResumeByUserId(user.getId());
			String param = "";
			if(resumeInfo != null){
				param = "?resumeId=" + resumeInfo.getId();
			}
			Map<String, String> resumeMenuMap = new HashMap<String, String>();
			resumeMenuMap.put("name", "Management");
			resumeMenuMap.put("value", "/interview/page");
			menus.add(resumeMenuMap);
			Map<String, String> infoMenuMap = new HashMap<String, String>();
			infoMenuMap.put("name", "Personal");
			infoMenuMap.put("value", "/info/page/add" + param);
			menus.add(infoMenuMap);
		}else{
			ResumeInfo resumeInfo = resumeService.getResumeByUserId(user.getId());
			String param = "";
			if(resumeInfo != null){
				param = "?resumeId=" + resumeInfo.getId();
			}
			Map<String, String> resumeMenuMap = new HashMap<String, String>();
			resumeMenuMap.put("name", "Resume");
			resumeMenuMap.put("value", "/interview/page");
			menus.add(resumeMenuMap);
			Map<String, String> infoMenuMap = new HashMap<String, String>();
			infoMenuMap.put("name", "Personal");
			infoMenuMap.put("value", "/info/page/add" + param);
			menus.add(infoMenuMap);
		}
		resp.setData(menus);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public ResponseModel listUsers(){
		log.info("@ listUsers");
		BaseResponse resp = new BaseResponse();
		List<UserInfo> listUsers = userService.listUsers();
		resp.setData(listUsers);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/set/emp",method=RequestMethod.POST)
	public ResponseModel setEmp( String email){
		BaseResponse resp = new BaseResponse();
		userService.setEmployee(email);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/set/{userId}/{locked}/status",method=RequestMethod.PUT)
	public ResponseModel setStatus(@PathVariable("userId") Long userId,@PathVariable("locked") String locked){
		BaseResponse resp = new BaseResponse();
		userService.lockUser(userId, locked);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/delete/{userId}",method=RequestMethod.DELETE)
	public ResponseModel delete(@PathVariable("userId") Long userId){
		BaseResponse resp = new BaseResponse();
		userService.deleteUser(userId);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/current",method=RequestMethod.GET)
	public ResponseModel findCurrentUser(HttpServletRequest request){
		log.info("@ public/user/current ");
		UserResponse resp = new UserResponse();
		User user = (User)SecurityContextUtil.getUserDetails();
		resp.setUser(user);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}

}
