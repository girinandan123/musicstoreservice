package com.cx.uioc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cx.uioc.dto.Response;
import com.cx.uioc.service.DatabaseFileService;

/**
 * FileUploadController Class - Controller for file uploading
 */
@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS}, maxAge = WebCorsHelper.MAX_AGE, methods = {RequestMethod.POST, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
@RequestMapping(value = RestApiHelper.STORAGE)
public class FileUploadController {
	/** Logger */
    private static final Logger logger = LogManager.getLogger(FileUploadController.class);

    /** path of upload file */
	public final static String UPLOAD_FILE = "/uploadFile";
	/** path of the upload multiple files */
	public final static String UPLOAD_MULTIPLE_FILES = "/upload-multiple-files";
    /**
     * Members
     */
    /** Service for file storage */
    @Autowired
    private DatabaseFileService fileStorageService;
        

    /**
     * Methods
     */
    /** Upload a file */
    @PostMapping(value = UPLOAD_FILE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    	logger.info("uploadFile [(file{})]", file);
    	
    	try {
    		// Store file to repository
        	Response response = fileStorageService.storeFile(file);

            return new ResponseEntity<Response>(response, HttpStatus.OK);
            
    	} catch (Exception e) {
    		return RestApiHelper.generateErrorResponse(logger, e);
    	}
    	
    }

    /** Upload the multiple files */
    @PostMapping(value = UPLOAD_MULTIPLE_FILES)
    public ResponseEntity<?> uploadMultipleFiles(@RequestParam("files") MultipartFile... files) {
    	logger.info("uploadMultileFiles [(files.length{})]", files.length);
    	
    	List<Response> responses = new ArrayList<>();
    	
    	List<MultipartFile> filesList = Arrays.asList(files);
    	try {
    		for (MultipartFile file : filesList) {
    			// Store file to repository
    			Response response = fileStorageService.storeFile(file);    			  			
            	
    			if (response != null) {
    				responses.add(response);
    			}
    		}
    		return new ResponseEntity<List<Response>>(responses, HttpStatus.OK);
    	} catch(Exception e) {
    		return RestApiHelper.generateErrorResponse(logger, e);
    	}    	
    }
}
