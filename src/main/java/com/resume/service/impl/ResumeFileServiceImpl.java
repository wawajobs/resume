package com.resume.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resume.dao.ResumeFileDao;
import com.resume.dto.ResumeFile;
import com.resume.po.ResumeFilePo;
import com.resume.service.ResumeFileService;
import com.resume.util.BeanUtil;

@Service
public class ResumeFileServiceImpl implements ResumeFileService {

	@Autowired
	private ResumeFileDao resumeFileDao;
	
	@Override
	public List<ResumeFile> getResumeFileByResumeIdAndType(long resumeId, int type) {
		
		List<ResumeFilePo> file = resumeFileDao.queryByResumeIdAndType(resumeId, type);
		return BeanUtil.createCopyList(file, ResumeFile.class);
	}
	
	@Override
	public List<ResumeFile> getResumeFileByResumeIdsAndType(List<Long> resumeIds, int type) {
		
		List<ResumeFilePo> files = resumeFileDao.queryByResumeIdsAndType(resumeIds, type);
		return BeanUtil.createCopyList(files, ResumeFile.class);
	}

	@Override
	public long saveResumeFile(ResumeFile resumeFile) {
		
		ResumeFilePo resumeFilePo = BeanUtil.createCopy(resumeFile, ResumeFilePo.class);
		resumeFileDao.insertResumeFile(resumeFilePo);
		return resumeFilePo.getId();
	}


	@Override
	public void deleteFile(long id) {
		resumeFileDao.deleteFile(id);
	}
	
	@Override
	public void downloadFile(long id) {
		resumeFileDao.downloadFile(id);
		
	}
	
	@Override
	public ResumeFile queryById(long id) {
		ResumeFilePo resumeFilePo = resumeFileDao.queryById(id);
		return BeanUtil.createCopy(resumeFilePo, ResumeFile.class);
	}

}
