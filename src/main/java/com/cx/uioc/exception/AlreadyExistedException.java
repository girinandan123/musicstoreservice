package com.cx.uioc.exception;

/**
 * AlreadyExistedException Class
 */
public class AlreadyExistedException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 6857505944839639681L;
	
	/**
	 * Constructor
	 */
	public AlreadyExistedException(String ...args) {
		super(ServiceErrCode.ERR_ALREADY_EXISTED, args);
	}
}
