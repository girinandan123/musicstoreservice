package com.cx.uioc.exception;

import org.springframework.http.HttpStatus;

/**
 * ErrCodeUtil Class - Utility for Error Code
 */
public final class ErrCodeUtil {
	/** Internal Constructor */
	private ErrCodeUtil() {}
	
	/** Parse the message */
	public static String parseMessage(String message, String ...args) {
		if (message == null || message.trim().length() <= 0) {
			return message;
		}
		
		if (args == null || args.length <= 0) {
			return message;
		}
		
		String[] splitMsgs = message.split("%");
		if (splitMsgs == null || splitMsgs.length <= 1) {
			return message;
		}
		
		String msg = message;
		for (int i = 0; i < args.length; i++) {
			String replaceChar = "%" + (i + 1);
			msg = msg.replaceFirst(replaceChar, args[i]);
		}
		
		return msg;
	}
	
	/** Map error code to HTTP status */
	public static HttpStatus toHttpStatus(String errCode) {
		if (errCode == ServiceErrCode.ERR_NOT_EXISTED.getErrCode()) {
			return HttpStatus.NOT_FOUND;
		} else if (errCode == ServiceErrCode.ERR_ALREADY_EXISTED.getErrCode()) {
			return HttpStatus.CONFLICT;
		} else if (errCode == ServiceErrCode.ERR_SIZE_OVERFLOW.getErrCode()) {
			return HttpStatus.CONFLICT;
		} else if (errCode == ServiceErrCode.ERR_WRONG_ARGUMENT.getErrCode()) {
			return HttpStatus.BAD_REQUEST;
		} else if (errCode == ServiceErrCode.ERR_VALIDATION_FAIL.getErrCode()) {
			return HttpStatus.BAD_REQUEST;
		} else if (errCode == ServiceErrCode.ERR_NOT_IMPLEMENTED.getErrCode()) {
			return HttpStatus.NOT_IMPLEMENTED;
		}
		
		return HttpStatus.SERVICE_UNAVAILABLE;
	}	
}
