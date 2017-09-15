package com.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resume.po.FeedbackPo;

public interface FeedbackDao {

	Long insertFeedback(FeedbackPo feedbackPo);
	
	List<FeedbackPo> queryList(@Param("beginIndex")Integer beginIndex,@Param("size")Integer size);
}
