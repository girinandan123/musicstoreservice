package com.cx.uioc.common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * CommonUtils Class
 */
public final class CommonUtils {
	/** Logger */
    private static final Logger logger = LogManager.getLogger(CommonUtils.class.getName());

    /**
	 * Constants
	 */
    /** Minimum Identifier */
    public final static long MIN_ID = 1;
	/** Maximum length of URL */
	public final static int MAX_URL_LENGTH = 2084;
	/** Maximum length of name */
	public final static int MAX_NAME_LENGTH = 64;
	/** Maximum length of phone number */
	public final static int MAX_PHONE_LENGTH = 11;
	/** Maximum length of account */
	public final static int MAX_ACCOUNT_LENGTH = 64;
	/** Maximum length of address */
	public final static int MAX_ADDRESS_LENGTH = 255;
	/** Maximum length of hash */
	public final static int MAX_HASH_LENGTH = 255;
	
	/** Literal - Slash */
	private final static char SLASH = '/';

	/**
	 * Internal Constructor
	 */
	private CommonUtils() {
		// Do nothing
	}
	
	/**
	 * Methods
	 */
	/** Convert the object as JSON string */
	public static String asJsonString(final Object obj) throws Exception {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
        	logger.warn(e.getMessage());
        	throw e;
	    }
	}
	
	/** Convert the integers to CSV string */
	public static String asCSVString(List<Integer> integers) throws Exception {
		List<String> csvs = new ArrayList<String>();
		for (Integer value : integers) {
			csvs.add(value.toString());
		}
		
		return String.join(",", csvs);
	}

	/** Convert the integer strings separated colons to integers */
	public static List<Integer> asIntegers(String body) throws Exception {
		List<Integer> integers = new ArrayList<Integer>();
		if (body != null && !body.isEmpty()) {
			List<String> symbols = Arrays.asList(body.split(","));
			for (String symbol : symbols) {
				integers.add(Integer.parseInt(symbol));
			}
		}
		
		return integers;		
	}

	/** Convert the path strings separated colons to URLs by base URL */
	public static List<String> asURLs(String hintURL, String paths) throws Exception {
		String baseUrl = "";
		try {
			URL url = new URL(hintURL);
			baseUrl = url.getProtocol() + "://" + url.getAuthority();
			
		} catch (MalformedURLException e) {
			baseUrl = "https://localhost:8080";
		}
		
		List<String> urls = new ArrayList<String>();
		if (paths != null) {
			urls = Arrays.asList(paths.split(","));
			for (int i = 0; i < urls.size(); i++) {
				urls.set(i, String.join("/", baseUrl, urls.get(i)));
			}
		}

		return urls;
	}

	/** Convert the URL to paths without authority part */
	public static String asPathString(List<String> urls) throws Exception {
		List<String> paths = new ArrayList<String>();
		for (String url: urls) {
			try {
				String path = new URL(url).getPath();
				if (path.charAt(0) == SLASH) {
					paths.add(path.substring(1));
				} else {
					paths.add(path);
				}
			} catch (MalformedURLException e) {
				paths.add(url);
			}
		}
		
		return String.join(",", paths);
	}
}
