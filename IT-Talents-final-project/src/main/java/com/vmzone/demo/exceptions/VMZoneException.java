package com.vmzone.demo.exceptions;

import org.springframework.http.HttpStatus;

public class VMZoneException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5269002385341131219L;
	
	private String message;
	
	public VMZoneException() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public VMZoneException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
	public VMZoneException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	public VMZoneException(String message) {
		this.message = message;
		// TODO Auto-generated constructor stub
	}
	public VMZoneException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public String getMessage() {
		return message;
	}
	
	

}
