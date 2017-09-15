package com.resume.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.resume.dao.ResumeInfoDao;
import com.resume.dto.InterviewFlow;
import com.resume.dto.ResumeFile;
import com.resume.dto.ResumeInfo;
import com.resume.enums.FileType;
import com.resume.po.ResumeInfoPo;
import com.resume.service.InterviewFlowService;
import com.resume.service.ResumeFileService;
import com.resume.service.ResumeService;
import com.resume.util.BeanUtil;

@Service
public class ResumeServiceImpl implements ResumeService {
	
	@Autowired
	private ResumeInfoDao resumeInfoDao;
	
	@Autowired
	private ResumeFileService resumeFileService;
	
	@Autowired
	private InterviewFlowService interviewFlowService;

	@Override
	public ResumeInfo getResumeById(long id) {
		ResumeInfoPo resumeInfoPo = resumeInfoDao.queryById(id);
		return BeanUtil.createCopy(resumeInfoPo, ResumeInfo.class);
	}
	
	@Override
	public List<ResumeInfo> getResumeById(List<Long> ids) {
		List<ResumeInfoPo> pos = resumeInfoDao.queryByIds(ids);
		List<ResumeInfo> resumes = proccessResumeInfo(pos);
		
		return resumes;
	}
	
	@Override
	public ResumeInfo getResumeByUserId(long userId) {
		ResumeInfoPo resumeInfoPo = resumeInfoDao.queryByUserId(userId);
		return BeanUtil.createCopy(resumeInfoPo, ResumeInfo.class);
	}

	@Override
	@Transactional
	public ResumeInfo saveResumeInfo(ResumeInfo resumeInfo) {
		
		ResumeInfoPo resumeInfoPo = BeanUtil.createCopy(resumeInfo, ResumeInfoPo.class);
		resumeInfoDao.insertResumeInfo(resumeInfoPo);
		InterviewFlow interviewFlow = new InterviewFlow();
		interviewFlow.setCreateTime(new Date());
		interviewFlow.setCreatorId(resumeInfo.getCreatorId());
		interviewFlow.setResumeId(resumeInfoPo.getId());
		interviewFlow.setStep(1);
		interviewFlow.setUpdaterId(resumeInfo.getCreatorId());
		interviewFlowService.insertInterviewFlow(interviewFlow);
		return BeanUtil.createCopy(resumeInfoPo,ResumeInfo.class);
	}

	@Override
	public void updateResumeInfo(ResumeInfo resumeInfo) {
		if(null != resumeInfo){
			resumeInfoDao.updateResumeInfo(BeanUtil.createCopy(resumeInfo, ResumeInfoPo.class));
		}

	}

	@Override
	public void deleteResumeInfo(long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public List<ResumeInfo> getResumes(int page, int size) {
		int beginIndex = (page - 1) * size;
		
		List<ResumeInfoPo> listPo = resumeInfoDao.queryList(beginIndex, size);
		List<ResumeInfo> resumes = proccessResumeInfo(listPo);
		
		return resumes;
	}

	private List<ResumeInfo> proccessResumeInfo(List<ResumeInfoPo> listPo) {
		List<Long> resumeIds = new ArrayList<Long>();
		for (ResumeInfoPo resumeInfoPo : listPo) {
			resumeIds.add(resumeInfoPo.getId());
		}
		
		List<ResumeFile> files = resumeFileService.getResumeFileByResumeIdsAndType(resumeIds, FileType.RESUME_DOC.getCode());
		Map<Long, List<ResumeFile>> map = new HashMap<Long, List<ResumeFile>>();
		for (ResumeFile resumeFile : files) {
			List<ResumeFile> list = map.get(resumeFile.getResumeId());
			if(null == list){
				list = new ArrayList<ResumeFile>();
			}
			list.add(resumeFile);
			map.put(resumeFile.getResumeId(), list);
		}
		List<ResumeFile> resumeVideos = resumeFileService.getResumeFileByResumeIdsAndType(resumeIds, FileType.INTRODUCTION_VIDEO.getCode());
		Map<Long, List<ResumeFile>> videoMap = new HashMap<Long, List<ResumeFile>>();
		for (ResumeFile resumeFile : resumeVideos) {
			List<ResumeFile> list = videoMap.get(resumeFile.getResumeId());
			if(null == list){
				list = new ArrayList<ResumeFile>();
			}
			list.add(resumeFile);
			videoMap.put(resumeFile.getResumeId(), list);
		}
		
		List<ResumeInfo> resumes = new ArrayList<ResumeInfo>();
		for (ResumeInfoPo resumeInfoPo : listPo) {
			ResumeInfo resume = BeanUtil.createCopy(resumeInfoPo, ResumeInfo.class);
			resume.setResumeFiles(map.get(resumeInfoPo.getId()));
			resume.setVideos(videoMap.get(resumeInfoPo.getId()));
			resumes.add(resume);
		}
		return resumes;
	}

}
