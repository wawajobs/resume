package com.resume.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dao.InterviewFlowDao;
import com.resume.dto.InterviewFlow;
import com.resume.dto.ResumeFile;
import com.resume.dto.ResumeInfo;
import com.resume.dto.UserInfo;
import com.resume.enums.FileType;
import com.resume.po.InterviewFlowPo;
import com.resume.service.InterviewFlowService;
import com.resume.service.ResumeFileService;
import com.resume.service.ResumeService;
import com.resume.service.UserService;
import com.resume.util.BeanUtil;

@Service
public class InterviewFlowServiceImpl implements InterviewFlowService{
	
	@Autowired
	private InterviewFlowDao interviewFlowDao;
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private ResumeFileService resumeFileService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Long insertInterviewFlow(InterviewFlow interviewFlow){
		InterviewFlowPo interviewFlowPo = BeanUtil.createCopy(interviewFlow, InterviewFlowPo.class);
		interviewFlowDao.insertInterviewFlow(interviewFlowPo);
		return interviewFlowPo.getId();
	}
	
	@Override
	public void updateFlowStatus(InterviewFlow interviewFlow){
		InterviewFlowPo interviewFlowPo = BeanUtil.createCopy(interviewFlow, InterviewFlowPo.class);
		interviewFlowDao.updateFlowStatus(interviewFlowPo);
	}
	
	@Override
	public InterviewFlow findById(long id){
		InterviewFlowPo interviewFlowPo = interviewFlowDao.queryById(id);
		return BeanUtil.createCopy(interviewFlowPo, InterviewFlow.class);
	}
	
	@Override
	public InterviewFlow findByResumeId(long resumeId){
		InterviewFlowPo interviewFlowPo = interviewFlowDao.queryByResumeId(resumeId);
		return BeanUtil.createCopy(interviewFlowPo, InterviewFlow.class);
	}
	@Override
	public InterviewFlow findByResumeIdWithResume(long resumeId){
		InterviewFlowPo interviewFlowPo = interviewFlowDao.queryByResumeId(resumeId);
		InterviewFlow flow = BeanUtil.createCopy(interviewFlowPo, InterviewFlow.class);
		
		List<ResumeFile> tickets = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.FLIGHT_TICKET.getCode());
		if(null != tickets && tickets.size() > 0){
			flow.setFlightTicket(tickets.get(0));
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		List<Long> userIds = new ArrayList<Long>();
		userIds.add(resumeInfo.getCreatorId());
		List<UserInfo> user = userService.queryByIds(userIds);
		if(null != user && !user.isEmpty()){
			resumeInfo.setUserInfo(user.get(0));
		}
		flow.setResumeInfo(resumeInfo);
		return flow;
	}
	
	@Override
	public int countFlow(Long step) {
		// TODO Auto-generated method stub
		return interviewFlowDao.countFlow(step);
	}

	@Override
	public List<InterviewFlow> list(Long step, String col, String order,Integer page,Integer size) {
		Integer beginIndex = null;
		if(page != null){
			beginIndex = page;
		}
		List<InterviewFlowPo> listPos = interviewFlowDao.listPos(step, col, order, beginIndex, size);
		List<Long> resumeIds = new ArrayList<Long>();
		for (InterviewFlowPo interviewFlowPo : listPos) {
			resumeIds.add(interviewFlowPo.getResumeId());
		}
		
		if(resumeIds.isEmpty()){
			return new ArrayList<InterviewFlow>();
		}
		
		List<ResumeInfo> resumes = resumeService.getResumeById(resumeIds);
		Map<Long, ResumeInfo> map = new HashMap<Long, ResumeInfo>();
		List<Long> userIds = new ArrayList<Long>();
		for (ResumeInfo resumeInfo : resumes) {
			map.put(resumeInfo.getId(), resumeInfo);
			userIds.add(resumeInfo.getCreatorId());
		}
		
		List<UserInfo> userInfos = userService.queryByIds(userIds);
		Map<Long, UserInfo> userMap = new HashMap<Long, UserInfo>();
		if(null != userInfos){
			for (UserInfo userInfo : userInfos) {
				userMap.put(userInfo.getId(), userInfo);
			}
		}
		if(!userMap.isEmpty()){
			
			for (ResumeInfo resumeInfo : resumes) {
				resumeInfo.setUserInfo(userMap.get(resumeInfo.getCreatorId()));
			}
		}
		
		List<InterviewFlow> dtos = new ArrayList<InterviewFlow>();
		for (InterviewFlowPo interviewFlowPo : listPos) {
			InterviewFlow flow = BeanUtil.createCopy(interviewFlowPo, InterviewFlow.class);
			flow.setResumeInfo(map.get(flow.getResumeId()));
			dtos.add(flow);
		}
		
		return dtos;
	}
}
