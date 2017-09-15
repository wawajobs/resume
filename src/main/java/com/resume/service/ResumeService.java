package com.resume.service;

import java.util.List;

import com.resume.dto.ResumeInfo;

public interface ResumeService {
	
	/**
	 * 通过id获取简历信息
	 * @param id
	 * @return
	 */
	ResumeInfo getResumeById(long id);
	
	List<ResumeInfo> getResumeById(List<Long> ids);
	
	ResumeInfo getResumeByUserId(long userId);
	
	/**
	 * 请求简历信息
	 * @param page
	 * @param size
	 * @return
	 */
	List<ResumeInfo> getResumes(int page,int size);
	
	/**
	 * 保存简历信息
	 * @param resumeInfo
	 * @return 返回简历id
	 */
	ResumeInfo saveResumeInfo(ResumeInfo resumeInfo);
	
	/**
	 * 更新简历信息
	 * @param resumeInfo
	 */
	void updateResumeInfo(ResumeInfo resumeInfo);
	
	/**
	 * 删除简历信息
	 * @param id
	 */
	void deleteResumeInfo(long id);


}
