package com.cx.uioc.exception;

/**
 * ValidationFailException Class
 */
public class ValidationFailException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4458793847391233519L;

	/**
	 * Constructor
	 */
	public ValidationFailException(String ...args) {
		super(ServiceErrCode.ERR_VALIDATION_FAIL, args);
	}
}
