package com.resume.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dao.FeedbackDao;
import com.resume.dto.Feedback;
import com.resume.po.FeedbackPo;
import com.resume.service.FeedbackService;
import com.resume.util.BeanUtil;


@Service
public class FeedbackServiceImpl implements FeedbackService{

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Override
	public Long saveFeedback(Feedback feedback) {
		FeedbackPo feedbackPo = BeanUtil.createCopy(feedback, FeedbackPo.class);
		feedbackDao.insertFeedback(feedbackPo);
		return feedbackPo.getId();
	}

	@Override
	public List<Feedback> getFeedback(Integer page, Integer size) {
		Integer beginIndex = null;
		if(null != page && null != size){
			beginIndex = (page - 1)*size;
		}
		List<FeedbackPo> feedbackPos = feedbackDao.queryList(beginIndex, size);
		return BeanUtil.createCopyList(feedbackPos, Feedback.class);
	}

}
