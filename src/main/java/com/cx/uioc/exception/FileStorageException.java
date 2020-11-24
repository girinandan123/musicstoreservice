package com.cx.uioc.exception;

/**
 * FileStorageException Exception - Exception for file storage
 */
public class FileStorageException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructors
	 */
	/** With message */
	public FileStorageException(String message) {
        super(message);
    }

	/** With message and cause */
    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
