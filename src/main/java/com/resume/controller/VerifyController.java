package com.resume.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.resume.common.Constant;
import com.resume.util.VerifyCodeUtils;

@Controller
@RequestMapping("/public/verify")
public class VerifyController {

	@RequestMapping("/code")
	public void generateVerifyCode(HttpServletRequest request,HttpServletResponse response) throws IOException{
		 //生成验证码  
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);  
        //获取session  
        HttpSession session = request.getSession(true);  
        session.setAttribute(Constant.SESSION_GENERATED_VERIFY_KEY, verifyCode.toLowerCase());  
        //设置图片高度和宽度
        int w = 144, h = 56;  
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);  
	}
}
