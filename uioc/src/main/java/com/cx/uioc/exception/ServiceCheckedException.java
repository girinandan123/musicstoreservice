package com.cx.uioc.exception;

/**
 * ServiceCheckedException Class
 */
public class ServiceCheckedException extends AbstractServiceException {
	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = 106655870531262204L;

	/**
	 * Constructors
	 */
	/** With code and message */
	public ServiceCheckedException(String code, String message) {
		super(code, message);
	}
	/** With code and message, and throwable */
	public ServiceCheckedException(String code, String message, Throwable err) {
		super(code, message, err);
	}
	/** With error code and arguments */
	public ServiceCheckedException(ErrCodable errCodable, String ...args) {
		super(errCodable, args);
	}
}
