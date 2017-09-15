package com.resume.po;

import java.util.Date;

public class ResumeInfoPo {
	
	private long id;
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 年龄
	 */
	private String age;
	
	private String birthDate;
	
	/**
	 * 性别 f,m
	 */
	private String gender;
	
	/**
	 * 职位
	 */
	private String position;
	
	/**
	 * 手机号
	 */
	private String phone;
	
	/**
	 * 国籍
	 */
	private String citizenship;
	
	/**
	 * 教育背景
	 */
	private String education;
	
	/**
	 * 专业课
	 */
	private String major;
	
	/**
	 * 教育所在国家
	 */
	private String country;
	
	/**
	 * 证书
	 */
	private String certification;
	
	/**
	 * 专业技能
	 */
	private String specialized;
	
	/**
	 * 工作经验年限
	 */
	private String experienceLength;
	
	/**
	 * 其他职位
	 */
	private String otherPositions;
	
	private String salary;
	
	private String location;
	
	private String recommended;
	
	private long creatorId;
	
	private long updaterId;
	
	private Date createTime;
	
	private Date updateTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getSpecialized() {
		return specialized;
	}

	public void setSpecialized(String specialized) {
		this.specialized = specialized;
	}

	public String getExperienceLength() {
		return experienceLength;
	}

	public void setExperienceLength(String experienceLength) {
		this.experienceLength = experienceLength;
	}

	public String getOtherPositions() {
		return otherPositions;
	}

	public void setOtherPositions(String otherPositions) {
		this.otherPositions = otherPositions;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getRecommended() {
		return recommended;
	}

	public void setRecommended(String recommended) {
		this.recommended = recommended;
	}
	

}
