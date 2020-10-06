package com.cx.uioc.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cx.uioc.domain.DatabaseFile;
import com.cx.uioc.service.DatabaseFileService;

/**
 * FileDownloadController Class - Controller for file downloading
 */
@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS}, maxAge = WebCorsHelper.MAX_AGE, methods = {RequestMethod.POST, RequestMethod.OPTIONS}, allowedHeaders = {"Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers"}, exposedHeaders = {"Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"})
@RequestMapping(value = RestApiHelper.STORAGE)
public class FileDownloadController {
	/** Logger */
    private static final Logger logger = LogManager.getLogger(FileDownloadController.class);

    
    /**
     * Members
     */
    /** Service for file storage */
    @Autowired
    private DatabaseFileService fileStorageService;
    /** Service for recording download info **/
    
    /**
     * Methods
     */
    /** Download a file */
    @GetMapping(value = RestApiHelper.DOWNLOAD_FILE + "/{fileId}/{fileName:.+}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileId") String fileId, @PathVariable("fileName") String fileName, HttpServletRequest request) {
    	logger.info("download-file [fileId({}), fileName({})]", fileId, fileName);
    	
        // Load file as Resource
    	try {
    		DatabaseFile databaseFile = fileStorageService.getFile(fileId, fileName);
    		return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(databaseFile.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + databaseFile.getFileName() + "\"")
                    .body(new ByteArrayResource(databaseFile.getData()));
    	} catch(Exception e) {
    		return RestApiHelper.generateErrorResponse(logger, e);
    	}
        
    }
}
