package com.resume.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.resume.dto.InterviewFlow;
import com.resume.dto.ResumeInfo;
import com.resume.enums.BaseRoleType;
import com.resume.response.BaseResponse;
import com.resume.response.InterviewResponse;
import com.resume.response.ResponseModel;
import com.resume.service.InterviewFlowService;
import com.resume.service.ResumeService;
import com.resume.spring.security.SecurityContextUtil;
import com.resume.spring.security.User;

@Controller
@RequestMapping("/interview")
public class InterviewFlowController extends AbstractController{
	
	@Autowired
	private InterviewFlowService interviewFlowService;
	
	@Autowired
	private ResumeService resumeService;

	@RequestMapping("/page")
	public String showInterviewPage(Model model){
		log.info("@ interview/page ");
		User user = (User)SecurityContextUtil.getUserDetails();
		
		if(BaseRoleType.EMPLOYEE.getCode().equals(user.getRole()) ||
				BaseRoleType.ADMIN.getCode().equals(user.getRole())){
			if(BaseRoleType.ADMIN.getCode().equals(user.getRole())){
				model.addAttribute("adminUrl", "/user/page");
			}
			return "/manage/list";
		}
		
		ResumeInfo resume = resumeService.getResumeByUserId(user.getId());
		if(null == resume){
			return "redirect:/info/page/add";
		}
		model.addAttribute("resumeId", resume.getId());
		return "/flow";
		
	}
	
	@RequestMapping("/list")
	public String showResumeList(){
		log.info("@ interview/list ");
		
		return "/interview/list";
	}
	
	
	@ResponseBody
	@RequestMapping(value="/{resumeId}/{step}/update",method=RequestMethod.POST)
	public ResponseModel updateInterview(@PathVariable("step")Integer step,@PathVariable("resumeId")Long resumeId,Date arrivedDate,String accepted,Date visaDate,Date flightDate,String place){
		log.info("@ interview/page id:{}",new Object[]{resumeId});
		BaseResponse resp = new BaseResponse();
		if(null == step || null == resumeId){
			return resp.fail("Step and id cannot be null");
		}
		InterviewFlow flow = interviewFlowService.findById(resumeId);
		if(null == flow){
			return resp.fail("The resume is not exists");
		}
		
		if(step < 1){
			return resp.fail("This is the first step");
		}
		
		//检查是否是12步
		if(null != arrivedDate){
			if(12 != flow.getStep()){
				step = flow.getStep();
			}
		}
		
		User user = (User)SecurityContextUtil.getUserDetails();
		InterviewFlow interviewFlow = new InterviewFlow();
		interviewFlow.setUpdaterId(user.getId());
		interviewFlow.setStep(step);
		interviewFlow.setResumeId(resumeId);
		interviewFlow.setAccepted(accepted);
		interviewFlow.setArrivedDate(arrivedDate);
		interviewFlow.setVisaDate(visaDate);
		interviewFlow.setFlightDate(flightDate);
		interviewFlow.setPlace(place);
		interviewFlowService.updateFlowStatus(interviewFlow);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/{resumeId}/status",method=RequestMethod.GET)
	public ResponseModel interviewStatus(@PathVariable("resumeId")Long resumeId){
		log.info("@ interview/status resumeId:{}",new Object[]{resumeId});
		InterviewResponse resp = new InterviewResponse();
		if(null == resumeId){
			return resp.fail("resumeId cannot be null");
		}
		InterviewFlow flow = interviewFlowService.findByResumeId(resumeId);
		resp.setInterviewFlow(flow);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/{resumeId}/detail",method=RequestMethod.GET)
	public ResponseModel resumeDetail(@PathVariable("resumeId")Long resumeId){
		log.info("@ interview/status resumeId:{}",new Object[]{resumeId});
		InterviewResponse resp = new InterviewResponse();
		if(null == resumeId){
			return resp.fail("resumeId cannot be null");
		}
		InterviewFlow flow = interviewFlowService.findByResumeIdWithResume(resumeId);
		resp.setInterviewFlow(flow);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	
	@ResponseBody
	@RequestMapping(value="/flow/list",method=RequestMethod.POST)
	public ResponseModel listFlow(Long step,String col,String order,Integer page,Integer size){
		log.info("@ interview/flow/list step:{}",new Object[]{step});
		BaseResponse resp = new BaseResponse();
		if(null == step){
			step = 1L;
		}
		List<InterviewFlow> flows = interviewFlowService.list(step, col, order, page, size);
		resp.setData(flows);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	
	
	
	
}
