package com.cx.uioc.exception;

/**
 * SizeOverflowException Class
 */
public class SizeOverflowException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 2238793897391236519L;

	/**
	 * Constructor
	 */
	public SizeOverflowException(String ...args) {
		super(ServiceErrCode.ERR_SIZE_OVERFLOW, args);
	}
}
