package com.cx.uioc.exception;

/**
 * AbstractServiceException Class - Abstract Exception for Service
 */
public abstract class AbstractServiceException extends Exception {
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 5960207295705987145L;
	
	/**
	 * Properties
	 */
	/** Error Code */
	private String code;
	/** Error Message */
	private String message;
	
	/**
	 * Internal Constructors
	 */
	/** With message */
	private AbstractServiceException(String message) {
		super(message);
		
		this.message = message;
	}
	/** With code and message */
	protected AbstractServiceException(String code, String message) {
		this(message);
		
		this.code = code;
	}
	/** With message and throwable */
	private AbstractServiceException(String message, Throwable err) {
		super(message, err);
		
		this.message = message;
	}
	/** With code and message, and throwable */
	protected AbstractServiceException(String code, String message, Throwable err) {
		this(message, err);
		
		this.code = code;
	}
	/** With error code and arguments */
	protected AbstractServiceException(ErrCodable errCodable, String...args) {
		this(errCodable.getErrCode(), errCodable.getMessage(args));
	}
	
	/**
	 * Accesses
	 */
	/** Get the error code */
	public String getCode() {
		return code;
	}
	
	/** Get the error message */
	public String getMessage() {
		return message;
	}
}
