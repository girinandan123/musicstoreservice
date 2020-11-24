package com.cx.uioc.exception;

/**
 * WrongArgumentException Class
 */
public class NotImplementedException extends ServiceCheckedException {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 4958793897391226519L;

	/**
	 * Constructor
	 */
	public NotImplementedException(String ...args) {
		super(ServiceErrCode.ERR_NOT_IMPLEMENTED, args);
	}
}
