package com.cx.uioc.exception;

/**
 * NotExistedException Class
 */
public class NotExistedException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -1632450331963782985L;
	
	/**
	 * Constructor
	 */
	public NotExistedException(String ...args) {
		super(ServiceErrCode.ERR_NOT_EXISTED, args);
	}
}
