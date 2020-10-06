package com.cx.uioc.exception;

/**
 * WrongArgumentException Class
 */
public class WrongArgumentException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4958793897391236519L;

	/**
	 * Constructor
	 */
	public WrongArgumentException(String ...args) {
		super(ServiceErrCode.ERR_WRONG_ARGUMENT, args);
	}
}
