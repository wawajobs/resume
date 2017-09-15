package com.resume.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.resume.dto.ResumeFile;
import com.resume.dto.ResumeInfo;
import com.resume.enums.FileType;
import com.resume.files.BaseFile;
import com.resume.files.CertificationFile;
import com.resume.files.FlightFile;
import com.resume.files.IntroductionVideoFile;
import com.resume.files.PhotoFile;
import com.resume.response.BaseResponse;
import com.resume.response.ProgressResponse;
import com.resume.response.ResponseModel;
import com.resume.service.ResumeFileService;
import com.resume.service.ResumeService;
import com.resume.spring.security.SecurityContextUtil;
import com.resume.spring.security.User;

@Controller
@RequestMapping("/upload")
public class UploadController extends AbstractController{
	
	@Autowired
	private ResumeService resumeService;
	
	@Autowired
	private ResumeFileService resumeFileService;
	
	@RequestMapping("/{resumeId}/doc")
	public String showUploadPage(Model model,@PathVariable("resumeId")Long resumeId){
		
		model.addAttribute("resumeId", resumeId);
		return "/resume/upload_doc";
	}
	
//	@RequestMapping("/Video")
//	public String showUploadVideoPage(Model model,Long resumeId){
//		
//		model.addAttribute("resumeId", resumeId);
//		return "/resume/upload_doc";
//	}

	@ResponseBody
	@RequestMapping(value="/files/list",method=RequestMethod.POST)
	public ResponseModel getFiles(Long resumeId){
		log.info("@ upload/files/list resumeId:{}",new Object[]{resumeId});
		BaseResponse resp = new BaseResponse();
		
		List<ResumeFile> docs = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.RESUME_DOC.getCode());
		List<ResumeFile> photos = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.PHOTO.getCode());
		
		docs.addAll(photos);
		resp.setData(docs);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	@ResponseBody
	@RequestMapping(value="/files/{resumeId}/{type}",method=RequestMethod.POST)
	public ResponseModel getFiles(@PathVariable("resumeId")Long resumeId,@PathVariable("type")Integer type){
		log.info("@ upload/files/list resumeId:{}",new Object[]{resumeId});
		BaseResponse resp = new BaseResponse();
		
		List<ResumeFile> docs = resumeFileService.getResumeFileByResumeIdAndType(resumeId, type);
		
		resp.setData(docs);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}

	
	@RequestMapping(value="/resume",method=RequestMethod.POST)
	public String uploadResume(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId) {
		log.info("@ upload/resume resumeId:{}",new Object[]{resumeId});
		if(null == resumeId){
			model.addAttribute("error", "resumeId is not be null");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			model.addAttribute("error", "resume not exists");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			model.addAttribute("error", "this is not your resume");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		
		List<ResumeFile> docs = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.RESUME_DOC.getCode());
		if(null != docs && docs.size() >= 3){
			model.addAttribute("error", "Upload up to three files at most");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile filedata = ((MultipartHttpServletRequest) request)
					.getFile("resumeFile");
			try {
				String filePath = generateResumeFile(filedata,FileType.RESUME_DOC);
				String fileName = iso2Utf8(filedata.getOriginalFilename());
				if (filePath != null) {
					saveFile(resumeId,fileName, user, filePath,FileType.RESUME_DOC);
				} else {
					model.addAttribute("error", "please choose resmue file");
					return "redirect:/upload/"+resumeId+"/doc";
				}
				
			} catch (IOException e) {
				log.error("简历上传失败", e);
				model.addAttribute("error", "Resume upload failure");
				return "redirect:/upload/"+resumeId+"/doc";
			}
		}else{
			log.error("请求中不包含文件");
			model.addAttribute("error", "please choose resmue file");
			return "redirect:/upload/"+resumeId+"/doc";
		}	

		return "redirect:/upload/"+resumeId+"/doc";
	}
	
	@RequestMapping(value="/photo",method=RequestMethod.POST)
	public String uploadVideo(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId) {
		log.info("@ upload/photo resumeId:{}",new Object[]{resumeId});
		if(null == resumeId){
			model.addAttribute("error", "resumeId is not be null");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			model.addAttribute("error", "resume not exists");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			model.addAttribute("error", "this is not your resume");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		List<ResumeFile> photos = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.PHOTO.getCode());
		if(null != photos && photos.size() >= 3){
			model.addAttribute("error", "Upload up to three files at most");
			return "redirect:/upload/"+resumeId+"/doc";
		}
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile filedata = ((MultipartHttpServletRequest) request)
					.getFile("photoFile");
			try {
				String filePath = generateResumeFile(filedata,FileType.PHOTO);
				String fileName = iso2Utf8(filedata.getOriginalFilename());
				if (filePath != null) {
					saveFile(resumeId,fileName, user, filePath,FileType.PHOTO);
				} else {
					model.addAttribute("error", "please choose photo file");
					return "redirect:/upload/"+resumeId+"/doc";
				}
				
			} catch (IOException e) {
				log.error("简历上传失败", e);
				model.addAttribute("error", "photo upload failure");
				return "redirect:/upload/"+resumeId+"/doc";
			}
		}else{
			log.error("请求中不包含文件");
			model.addAttribute("error", "please choose photo file");
			return "redirect:/upload/"+resumeId+"/doc";
		}

		return "redirect:/upload/"+resumeId+"/doc";
	}
	
	@ResponseBody
	@RequestMapping(value="/check/condition",method=RequestMethod.POST)
	public ResponseModel checkeVideoCondition(Long resumeId){
		BaseResponse resp = new BaseResponse();
		if(null == resumeId){
			return resp.fail("resumeId is not be null");
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			return resp.fail("resume not exists");
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			return resp.fail("this is not your resume");
		}
		
		List<ResumeFile> videos = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.INTRODUCTION_VIDEO.getCode());
		if(null != videos && videos.size() >= 3){
			return resp.fail("Upload up to three files at most");
		}
		
		return  resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping(value="/intro/video",method=RequestMethod.POST)
	public ResponseModel introVideo(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId,String fileType){
		BaseResponse resp = new BaseResponse();
		if(null == resumeId){
			return resp.fail("resumeId is not be null");
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			return resp.fail("resume not exists");
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			return resp.fail("this is not your resume");
		}
		
		List<ResumeFile> videos = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.INTRODUCTION_VIDEO.getCode());
		if(null != videos && videos.size() >= 3){
			return resp.fail("Upload up to three files at most");
		}
		
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile filedata = ((MultipartHttpServletRequest) request)
					.getFile("video");
			if(null != filedata && filedata.getSize() != 0 && "video".equals(fileType)){
				try {
					
					
					String filePath = generateResumeFile(filedata,FileType.INTRODUCTION_VIDEO);
					String fileName = iso2Utf8(filedata.getOriginalFilename());
					if (filePath != null) {
						saveFile(resumeId,fileName, user, filePath,FileType.INTRODUCTION_VIDEO);
					} else {
						return resp.fail("please choose video file");
					}
					
				} catch (IOException e) {
					log.error("简历上传失败", e);
					return resp.fail("video upload failure");
				}
			}
		}	
		
		return  resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	
	
	@RequestMapping(value="/video",method=RequestMethod.POST)
	public String uploadPhoto(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId,String fileType) {
		log.info("@ upload/video resumeId:{} fileType:{}",new Object[]{resumeId,fileType});
		if(null == resumeId){
			model.addAttribute("error","resumeId is not be null");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			model.addAttribute("error","resume not exists");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			model.addAttribute("error","this is not your resume");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile certificationFiledata = ((MultipartHttpServletRequest) request)
					.getFile("certification");
			if(null != certificationFiledata && certificationFiledata.getSize() != 0  && "certification".equals(fileType)){
				try {
					List<ResumeFile> videos = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.CERTIFICATION.getCode());
					if(null != videos && videos.size() >= 3){
						model.addAttribute("error", "Upload up to three files at most");
						return "redirect:/upload/"+resumeId+"/doc";
					}
					String filePath = generateResumeFile(certificationFiledata,FileType.CERTIFICATION);
					String fileName = iso2Utf8(certificationFiledata.getOriginalFilename());
					if (filePath != null) {
						saveFile(resumeId,fileName, user, filePath,FileType.CERTIFICATION);
					} else {
						model.addAttribute("error","please choose certification file");
						model.addAttribute("resumeId", resumeId);
						return "redirect:/interview/page";
					}
					
				} catch (IOException e) {
					log.error("简历上传失败", e);
					model.addAttribute("error","certification upload failure");
					model.addAttribute("resumeId", resumeId);
					return "redirect:/interview/page";
				}
			}else{
				log.error("请求中不包含文件");
				model.addAttribute("error","please choose a file");
				model.addAttribute("resumeId", resumeId);
				return "redirect:/interview/page";
			}
		}else{
			log.error("请求中不包含文件");
			model.addAttribute("error","please choose a file");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		
		return "redirect:/interview/page";
	}
	
	@ResponseBody
	@RequestMapping(value="/getProgress",method=RequestMethod.POST)
	public ResponseModel getProgress(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		ProgressResponse resp = new ProgressResponse();
		HttpSession session = request.getSession();
		Object percent = session.getAttribute("videoFile");
		log.error("percent:" + percent);
		if(null == percent){
			return resp.fail("There are no files being uploaded");
		}
		resp.setPercent(percent);
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@ResponseBody
	@RequestMapping("/certification")
	public ResponseModel uploadCertification(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId) {
		log.info("@ upload/certification resumeId:{}",new Object[]{resumeId});
		BaseResponse resp = new BaseResponse();
		if(null == resumeId){
			return resp.fail("resumeId is not be null");
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			return resp.fail("resume not exists");
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			
			return resp.fail("this is not your resume");
		}
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile filedata = ((MultipartHttpServletRequest) request)
					.getFile("certification");
			try {
				String filePath = generateResumeFile(filedata,FileType.CERTIFICATION);
				String fileName = iso2Utf8(filedata.getOriginalFilename());
				if (filePath != null) {
					saveFile(resumeId,fileName, user, filePath,FileType.CERTIFICATION);
				} else {
					return resp.fail("please choose certification file");
				}
				
			} catch (IOException e) {
				log.error("简历上传失败", e);
				return resp.fail("certification upload failure");
			}
		}else{
			log.error("请求中不包含文件");
			return resp.fail("please choose certification file");
		}
		
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	
	@RequestMapping(value="/ticket",method=RequestMethod.POST)
	public String uploadFlightTicket(Model model, HttpServletRequest request,
			HttpServletResponse response,Long resumeId) {
		log.info("@ upload/certification resumeId:{}",new Object[]{resumeId});
		if(null == resumeId){
			model.addAttribute("error","resumeId is not be null");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		ResumeInfo resumeInfo = resumeService.getResumeById(resumeId);
		if(null == resumeInfo){
			model.addAttribute("error","resume not exists");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		//获取当前登录用户
		User user = (User)SecurityContextUtil.getUserDetails();
		if(user.getId() != resumeInfo.getCreatorId()){
			model.addAttribute("error","this is not your resume");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		if (request instanceof MultipartHttpServletRequest) {
			MultipartFile filedata = ((MultipartHttpServletRequest) request)
					.getFile("flightTicket");
			try {
				List<ResumeFile> tickets = resumeFileService.getResumeFileByResumeIdAndType(resumeId, FileType.FLIGHT_TICKET.getCode());
				if(null != tickets && tickets.size() >= 1){
					model.addAttribute("error","You have already uploaded your ticket");
					model.addAttribute("resumeId", resumeId);
					return "redirect:/interview/page";
				}
				String filePath = generateResumeFile(filedata,FileType.FLIGHT_TICKET);
				String fileName = iso2Utf8(filedata.getOriginalFilename());
				if (filePath != null) {
					saveFile(resumeId,fileName, user, filePath,FileType.FLIGHT_TICKET);
				} else {
					model.addAttribute("error","please choose flight ticket file");
					model.addAttribute("resumeId", resumeId);
					return "redirect:/interview/page";
				}
				
			} catch (IOException e) {
				log.error("简历上传失败", e);
				model.addAttribute("error","flight ticket upload failure");
				model.addAttribute("resumeId", resumeId);
				return "redirect:/interview/page";
			}
		}else{
			log.error("请求中不包含文件");
			model.addAttribute("error","please choose flight ticket file");
			model.addAttribute("resumeId", resumeId);
			return "redirect:/interview/page";
		}
		
		model.addAttribute("resumeId", resumeId);
		return "redirect:/interview/page";
	}
	
	@RequestMapping("/{id}/download")
	public String download(Model model,@PathVariable("id")Long id){
		
		ResumeFile resumeFile = resumeFileService.queryById(id);
		if(null == resumeFile){
			model.addAttribute("error", "the file is not exists");
			return "redirect:/error.jsp";
		}
		//标记下载文件
		resumeFileService.downloadFile(id);
		
		return "redirect:" + resumeFile.getFileAddress();
	}
	
	@ResponseBody
	@RequestMapping(value="/{id}/delete",method=RequestMethod.POST)
	public ResponseModel delete(Model model,@PathVariable("id")Long id){
		
		BaseResponse resp = new BaseResponse();
		resumeFileService.deleteFile(id);
		
		return resp.success(BaseResponse.SUCCESS_MESSAGE);
	}
	

	private void saveFile(Long resumeId, String fileName,User user, String filePath,FileType fileType) {
		com.resume.dto.ResumeFile fileDto = new com.resume.dto.ResumeFile();
		fileDto.setResumeId(resumeId);
		fileDto.setFileType(filePath.substring(filePath.lastIndexOf(".")+1));
		fileDto.setFileAddress(filePath);
		fileDto.setType(fileType.getCode());
		fileDto.setUserId(user.getId());
		fileDto.setFileName(fileName);
		resumeFileService.saveResumeFile(fileDto);
	}
	
	private String generateResumeFile(MultipartFile filedata,FileType fileType)
			throws IOException {
		if (filedata != null) {
			String fileName = iso2Utf8(filedata.getOriginalFilename());
			BaseFile resumeFile = null;
			if(FileType.INTRODUCTION_VIDEO.equals(fileType)){
				resumeFile = new IntroductionVideoFile(fileName);
			}else if(FileType.RESUME_DOC.equals(fileType)){
				resumeFile = new com.resume.files.ResumeFile(fileName);
				
			}else if(FileType.PHOTO.equals(fileType)){
				resumeFile = new PhotoFile(fileName);
				
			}else if(FileType.CERTIFICATION.equals(fileType)){
				resumeFile = new CertificationFile(fileName);
				
			}else if(FileType.FLIGHT_TICKET.equals(fileType)){
				resumeFile = new FlightFile(fileName);
				
			}
			if(null != resumeFile){
				String filePath = resumeFile.saveFile(filedata
						.getInputStream());
				
				return filePath;
			}
		}
		return null;
	}
	
	public String iso2Utf8(String str){
		String utf8Str;
		try {
			utf8Str = new String(str.getBytes("ISO8859-1"),"UTF8");
		} catch (UnsupportedEncodingException e) {
			log.error("upload file error",e);
			return str;
		}
		return utf8Str;
	}
}
