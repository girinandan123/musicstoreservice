package com.cx.uioc.exception;

/**
 * ErrCodable Interface
 */
public interface ErrCodable {
	/**
	 * Methods
	 */
	/** Get the error code */
	String getErrCode();
	/** Get the argued error message */
	String getMessage(String ...args);
}
