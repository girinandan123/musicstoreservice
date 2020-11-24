package com.cx.uioc.dto;

import lombok.Data;

/**
 * Response Class
 */
@Data
public class Response {
	/**
	 * Properties
	 */
	/** File name */
    private String fileName;
    /** Download URI of file */
    private String fileDownloadUri;
    /** Type of file */
    private String fileType;
    /** Size of file */
    private long size;

    /**
     * Constructors
     */
    public Response(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName        = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType        = fileType;
        this.size            = size;
    }
}
