package com.resume.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * controller父类
 * 将所有controller的共有属性放到此类中
 * @author LiShuai
 *
 */
public abstract class AbstractController {

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	@InitBinder    
	   protected void initBinder(WebDataBinder binder) {    
	       binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));    
//	/        binder.registerCustomEditor(int.class, new CustomNumberEditor(int.class, true));    
//	       binder.registerCustomEditor(int.class, new IntegerEditor());    
//	/        binder.registerCustomEditor(long.class, new CustomNumberEditor(long.class, true));  
//	       binder.registerCustomEditor(long.class, new LongEditor());    
//	       binder.registerCustomEditor(double.class, new DoubleEditor());    
//	       binder.registerCustomEditor(float.class, new FloatEditor());    
	   }  
}
