package com.cx.uioc.controller;

import java.util.Arrays;
import java.util.List;

/**
 * CorsUtils Class
 */
public final class WebCorsHelper {
	/** Origins */
	public final static String ORIGINS = "*";
	/** Max Age */
	public final static int MAX_AGE = 4800;
	/** Allow Credentials */
	public final static String ALLOW_CREDENTIALS = "false";
	
	/** Expose Headers */
	public final static List<String> EXPOSE_HEADERS = Arrays.asList("Location");
}
