package com.resume.dto;

import java.io.Serializable;
import java.util.Date;

public class InterviewFlow implements Serializable{

	private static final long serialVersionUID = 3959018665024075161L;

	private long id;
	
	private long resumeId;
	
	private int step;
	
	private String received;
	
	private String accepted;
	
	private Date arrivedDate;
	
	private Date visaDate;
	
	private String refused;
	
	private String place;
	
	private Date flightDate;
	
	private long creatorId;
	
	private long updaterId;
	
	private Date createTime;
	
	private Date updateTime;
	
	private ResumeInfo resumeInfo;
	
	private ResumeFile flightTicket;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getResumeId() {
		return resumeId;
	}

	public void setResumeId(long resumeId) {
		this.resumeId = resumeId;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(long creatorId) {
		this.creatorId = creatorId;
	}

	public long getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(long updaterId) {
		this.updaterId = updaterId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	public Date getArrivedDate() {
		return arrivedDate;
	}

	public void setArrivedDate(Date arrivedDate) {
		this.arrivedDate = arrivedDate;
	}

	public String getRefused() {
		return refused;
	}

	public void setRefused(String refused) {
		this.refused = refused;
	}

	public Date getVisaDate() {
		return visaDate;
	}

	public void setVisaDate(Date visaDate) {
		this.visaDate = visaDate;
	}

	public ResumeInfo getResumeInfo() {
		return resumeInfo;
	}

	public void setResumeInfo(ResumeInfo resumeInfo) {
		this.resumeInfo = resumeInfo;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public ResumeFile getFlightTicket() {
		return flightTicket;
	}

	public void setFlightTicket(ResumeFile flightTicket) {
		this.flightTicket = flightTicket;
	}
	
	
}
