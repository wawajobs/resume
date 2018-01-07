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

	/**
	 * 登录后业务首页
	 * 管理员直接跳管理页面
	 * 还未添加个人信息的客户跳转到个人信息维护页面
	 * 其他情况个人状态流程页面
	 * @author LiShuai
	 * @param model
	 * @return
	 */
	@RequestMapping("/page")
	public String showInterviewPage(Model model){
		log.info("@ interview/page ");
		User user = (User)SecurityContextUtil.getUserDetails();
		//检查用户角色
		if(BaseRoleType.EMPLOYEE.getCode().equals(user.getRole()) ||
				BaseRoleType.ADMIN.getCode().equals(user.getRole())){
			if(BaseRoleType.ADMIN.getCode().equals(user.getRole())){
				model.addAttribute("adminUrl", "/user/page");
			}
			return "/manage/list";
		}
		//查询个人信息是否已经填写
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
	
	
	/**
	 * 更新简历筛选步骤
	 * @author LiShuai
	 * @param step 进行到第几步
	 * @param resumeId 简历id
	 * @param arrivedDate 到达中国时间
	 * @param accepted 是否接受offer
	 * @param visaDate 签证日期
	 * @param flightDate 航班日期
	 * @param place 到达中国地点
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/{resumeId}/{step}/update",method=RequestMethod.POST)
	public ResponseModel updateInterview(@PathVariable("step")Integer step,@PathVariable("resumeId")Long resumeId,Date arrivedDate,String accepted,Date visaDate,Date flightDate,String place){
		log.info("@ interview/page id:{}",new Object[]{resumeId});
		BaseResponse resp = new BaseResponse();
		//检查参数
		if(null == step || null == resumeId){
			return resp.fail("Step and id cannot be null");
		}
		//检查信息是否存在
		InterviewFlow flow = interviewFlowService.findById(resumeId);
		if(null == flow){
			return resp.fail("The resume is not exists");
		}
		//检查步骤是否合法
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
	
	/**
	 * 查询当前用户的状态信息
	 * @author LiShuai
	 * @param resumeId
	 * @return
	 */
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
	
	@ResponseBody
	@RequestMapping(value="/flow/count",method=RequestMethod.POST)
	public ResponseModel countFlow(Long step){
		log.info("@ interview/flow/count step:{}",new Object[]{step});
		BaseResponse resp = new BaseResponse();
		if(null == step){
			step = 1L;
		}
		int count = interviewFlowService.countFlow(step);
		resp.setCount(count);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	
	
	
	
}
