package com.resume.service.exception;

public class UserException extends Exception{

	private static final long serialVersionUID = 8987101243530293801L;
	
	private int code;

	public UserException() {
		super();
	}
	public UserException(UserExceptionType userExceptionType) {
		super(userExceptionType.getMessage());
		this.code = userExceptionType.getCode();
	}

	public UserException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public UserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UserException(String arg0) {
		super(arg0);
	}

	public UserException(Throwable arg0) {
		super(arg0);
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}



	public enum UserExceptionType{
		
		USER_NOT_EXISTS(0,"User don't exist"),
		USER_EXISTS(0,"User already exist");
		
		private int code;
		
		private String message;
		
		private UserExceptionType(int code,String message){
			this.code = code;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
		 
		
	}
	
	

}
