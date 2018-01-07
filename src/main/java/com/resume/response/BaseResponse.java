package com.resume.response;

import java.util.List;

public class BaseResponse implements ResponseModel {

	private static final long serialVersionUID = 7228098110889625861L;
	
	public static final int STATUS_SUCCESS = 1;
	
	public static final int STATUS_ERROR = 0;
	
	public static final String SUCCESS_MESSAGE = "OK";
	
	public static final String MISSING_PARAMETER_MESSAGE = "参数缺失";
	
	private int status;
	
	private String message = SUCCESS_MESSAGE;
	
	private List<?> data;
	
	private int count;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}
	
	public BaseResponse success(String message) {
	    setStatus(STATUS_SUCCESS);
	    setMessage(message);
	    return this;
	}
	
	public BaseResponse fail(String message) {
        setStatus(STATUS_ERROR);
        setMessage(message);
        return this;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	

}
