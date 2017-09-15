package com.resume.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.resume.po.ResumeInfoPo;

public interface ResumeInfoDao {
	
	
	/**
	 * 根据id查询resume
	 * @param id
	 * @return
	 */
	ResumeInfoPo queryById(long id);
	
	/**
	 * 根据id查询resume
	 * @param id
	 * @return
	 */
	List<ResumeInfoPo> queryByIds(@Param("ids")List<Long> ids);
	
	/**
	 * 根据id查询resume
	 * @param id
	 * @return
	 */
	ResumeInfoPo queryByUserId(long userId);
	
	/**
	 * 保存个人信息
	 * @param resumeInfoPo
	 */
	Long insertResumeInfo(ResumeInfoPo resumeInfoPo);
	
	/**
	 * 更新个人信息
	 * @param resumeInfoPo
	 */
	void updateResumeInfo(ResumeInfoPo resumeInfoPo);
	
	/**
	 * 分页查询简历信息
	 * @param beginIndex
	 * @param size
	 * @return
	 */
	List<ResumeInfoPo> queryList(@Param("beginIndex")int beginIndex,@Param("size")int size);

}
