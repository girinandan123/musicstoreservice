package com.cx.uioc.exception;

/**
 * ServiceErrCode Enumeration
 */
public enum ServiceErrCode implements ErrCodable {
	/** Not Existed */
	ERR_NOT_EXISTED("ERR_NOT_EXISTED", "{\"error\": \"%1 is not eixsted\"}"),
	/** Already Existed */
	ERR_ALREADY_EXISTED("ERR_ALREADY_EXISTED", "{\"error\": \"%1 is already existed\"}"),
	/** Overflow */
	ERR_SIZE_OVERFLOW("ERR_SIZE_OVERFLOW", "{\"error\": \"size of %1 overflow\"}"),
	/** Wrong Argument */
	ERR_WRONG_ARGUMENT("ERR_WRONG_ARGUMENT", "{\"error\": \"%1 is wrong argument\"}"),
	/** Validation Fail */
	ERR_VALIDATION_FAIL("ERR_VALIDATION_FAIL", "{\"error\": \"%1\"}"),
	/** Not Implemented */
	ERR_NOT_IMPLEMENTED("ERR_NOT_IMPLEMENTED", "{\"error\": \"%1 is not implemented\"}");
	
	/**
	 * Properties
	 */
	/** Error Code */
	private String errCode;
	/** Message */
	private String msg;
	
	/**
	 * Methods
	 */
	/** Get the error code */
	@Override
	public String getErrCode() {
		return this.errCode;
	}
	
	/** Get the argued error message */
	@Override
	public String getMessage(String ...args) {
		return ErrCodeUtil.parseMessage(this.msg, args);
	}
	
	/**
	 * Internal Constructor
	 */
	ServiceErrCode(String errCode, String msg) {
		this.errCode = errCode;
		this.msg = msg;
	}
}
