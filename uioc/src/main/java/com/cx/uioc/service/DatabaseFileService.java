package com.cx.uioc.service;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cx.uioc.controller.RestApiHelper;
import com.cx.uioc.domain.DatabaseFile;
import com.cx.uioc.dto.Response;
import com.cx.uioc.exception.FileStorageException;
import com.cx.uioc.repositories.DatabaseFileRepository;

/**
 * DatabaseFileService Class - Service for database file
 */
@Service
public class DatabaseFileService {
	/**
	 * Members
	 */
	/** Repository for file database */
    @Autowired
    private DatabaseFileRepository dbFileRepository;
      

    /**
     * Methods
     */
    /** Store the file */
    public Response storeFile(MultipartFile file) throws Exception {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        // Check if the file's name contains invalid characters
        if (fileName.contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
        }
                
        
        // Check if the same file existed
//        if (dbFileRepository.existsByData(file.getBytes())) {
//        	throw new AlreadyExistedException(fileName + " file");
//        }
        
        DatabaseFile dbFile = new DatabaseFile(fileName, file.getContentType(), file.getBytes());
        
        dbFile = dbFileRepository.save(dbFile);
        
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RestApiHelper.STORAGE + RestApiHelper.DOWNLOAD_FILE + "/")
                .path(dbFile.getFileId() + "/")
                .path(dbFile.getFileName())
                .toUriString();
        
        return new Response(dbFile.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());

    }

    /** Retrieve the file */
    public DatabaseFile getFile(String fileId, String fileName) throws Exception {
        return dbFileRepository.findByFileIdAndFileName(fileId, fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
    
    
    
    /** Get all files info **/
	public List<Response> getAllFilesInfo() throws Exception {
		// Container
		List<Response> responses = new ArrayList<>();
		// Get all files info from repository
		List<DatabaseFile> dbFiles = dbFileRepository.findAll();
		// From files info to container
		for (DatabaseFile dbFile : dbFiles) {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                .path("/downloadFile/")
	                .path(dbFile.getFileId() + "/")
	                .path(dbFile.getFileName())
	                .toUriString();
			Response response = new Response(dbFile.getFileName(), fileDownloadUri, dbFile.getFileId(), dbFile.getData().length);
			if (response != null) {
				responses.add(response);	// Add to the container
			}
		}
		return responses;
	}
}
