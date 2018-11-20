package com.shop.exception;

public class BusinessException extends Exception {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public BusinessException(String message) {
		this.message = message;
	}

	public BusinessException() {
	}
}
