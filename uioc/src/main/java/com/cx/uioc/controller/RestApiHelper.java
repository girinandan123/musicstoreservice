package com.cx.uioc.controller;

import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cx.uioc.exception.ErrCodeUtil;
import com.cx.uioc.exception.ServiceCheckedException;

/**
 * RestApiHelper Class
 */
public final class RestApiHelper {
	/**
	 * Constants
	 */
	/** Root Paths */
	/** Service Name */
	public final static String SERVICE_PATH = "/service";
	/** Service Version 1 */
	public final static String API_VER1 = "/v1";
	/** Service Version 2 */
	public final static String API_VER2 = "/v2";
	/** Service Version 3 */
	public final static String API_VER3 = "/v3";
	
    /** Path Variables */
    /** Identifier Path */
    public final static String ID_PATH = "/{id}";
    /** Identifier Variable */
    public final static String ID_VAR  = "id";

	/** Request Parameters */
    /** Page Parameter */
    public final static String PAGE_PARAM = "page";
    /** Size Parameter */
    public final static String SIZE_PARAM = "size";
    /** Default Page Value */
    public final static String DEF_PAGE = "0";
    /** Default Size value */
    public final static String DEF_SIZE = "0x7FFFFFFF";

    /** Counting Parameter */
    public final static String COUNTING_PARAM = "counting";
    /** Default Counting Value */
    public final static String DEF_COUNTING = "false";
    
    /** Format Parameter */
    public final static String SEARCH_PARAM = "search";
    
   /** Time Range Parameter */
    public final static String TIMERANGE_PARAM = "timerange";
    
    /** Name parameter */
    public final static String NAME_PARAM = "name";
    
    /** Password parameter */
	public static final String PASSWORD_VAR = "password";
	
	/** Path of the administrator */
	public final static String ADMIN = "/admin";
	/** Path of the storage */
	public final static String STORAGE = "/storage";
	/** Path of the download file */
	public final static String DOWNLOAD_FILE = "/download-file";
	/** Path of the audit */
	public final static String AUDIT = "/audit";
    /** Path of the store */
	public final static String STORE = "/store";
	/** Path of the pay */
	public final static String PAYPAL = "/paypal";
    /**
     * Internal constructor
     */
    private RestApiHelper() {
    	// Do nothing
    }
    
    /**
     * Methods
     */
    /** Generate the error response */
    public static ResponseEntity<?> generateErrorResponse(Logger logger, Exception exc) {
    	if (ServiceCheckedException.class.isInstance(exc)) {
    		// Service Error
    		logger.warn(exc.getMessage());
    		return new ResponseEntity<String>(exc.getMessage(), ErrCodeUtil.toHttpStatus(((ServiceCheckedException)exc).getCode()));
    	} else {
    		// Internal Server Error
    		logger.error(exc.getMessage());
    		return new ResponseEntity<String>(exc.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    }
}
