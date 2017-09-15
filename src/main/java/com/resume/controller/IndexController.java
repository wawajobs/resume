package com.resume.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resume.common.Constant;
import com.resume.spring.security.SecurityContextUtil;
import com.resume.spring.security.User;

@Controller
@RequestMapping("/view")
public class IndexController extends AbstractController{

	@RequestMapping("/index")
	public String index(Model model,HttpServletRequest request){
		User user = (User)SecurityContextUtil.getUserDetails();
		return "redirect:" + Constant.BASE_URL + "/index.html?user="+user;
	}
}
