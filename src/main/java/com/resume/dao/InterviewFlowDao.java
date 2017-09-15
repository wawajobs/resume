package com.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resume.po.InterviewFlowPo;

public interface InterviewFlowDao {
	
	/**
	 * 保存筛选进度
	 * @param interviewFlowPo
	 * @return
	 */
	Long insertInterviewFlow(InterviewFlowPo interviewFlowPo);
	
	/**
	 * 更新
	 * @param interviewFlowPo
	 */
	void updateFlowStatus(InterviewFlowPo interviewFlowPo);
	
	/**
	 * 根据id查询进度
	 * @param id
	 * @return
	 */
	InterviewFlowPo queryById(long id);
	
	/**
	 * 根据简历id查询进度
	 * @param resumeId
	 * @return
	 */
	InterviewFlowPo queryByResumeId(long resumeId);
	
	List<InterviewFlowPo> listPos(@Param("step")long step,@Param("col")String col,@Param("order")String order,@Param("beginIndex")Integer beginIndex,@Param("size")Integer size);
	
	

}
