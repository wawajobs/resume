package com.resume.service;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.resume.dto.ResumeInfo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/spring-context.xml","classpath:/spring-jdbc.xml"})
@Ignore
public class ResumeInfoServiceTest {

	@Autowired
	private ResumeService resumeService;
	
	@Test
	public void testSaveResumeInfo(){
		ResumeInfo resumeInfo = new ResumeInfo();
		resumeInfo.setAge("");
		resumeInfo.setCertification("毕业证，学位证，奖学金证书");
		resumeInfo.setCitizenship("china");
		resumeInfo.setCountry("america");
		resumeInfo.setCreateTime(new Date());
		resumeInfo.setCreatorId(1);
		resumeService.saveResumeInfo(resumeInfo);
	}
	
}
