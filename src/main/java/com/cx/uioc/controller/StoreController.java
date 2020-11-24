package com.cx.uioc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cx.uioc.dto.MusicDTO;
import com.cx.uioc.dto.MusicStoreDTO;
import com.cx.uioc.service.StoreService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS}, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.STORE)
@Api(value = "StoreController", tags = "Music store Management")
public class StoreController {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(StoreController.class);
	
	/**
	 * Constants
	 */
	/** Paths */
	/** Path of the music */
	public final static String MUSICS = "/musics";
	/** Path of the passwords */
	public final static String PASSWORDS_PATH = "/passwords";
	
	
	/** Messages */
    /** Success */
    private final static String msg_success = "Success | OK";

	/**
	 * Members
	 */
	/** Service for music management */
	@Autowired
	private StoreService storeService;
	
	/**
	 * Methods
	 */
    
    /**
     * Get all musics
     */
    @ApiOperation(value = "Get all musics where the audit status is passed")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = msg_success, response = MusicDTO.class, responseContainer = "List"),
			
	})
    @GetMapping(value = MUSICS)
	public ResponseEntity<?> getMusics() {
		logger.info(StoreController.class.getName() + ".getMusics [({})]");
		
		try {
			// Get all musics where the audit status is passed
			List<MusicStoreDTO> dtos = storeService.getMusics();
			return new ResponseEntity<List<MusicStoreDTO>>(dtos, HttpStatus.OK);
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
    
    /**
     * Get music contents URL by music identifier
     */
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = msg_success, response = HashMap.class),
    		@ApiResponse(code = 404, message = "Not found the music specified by identifier to delete", response = String.class)
    })
    @GetMapping(value = MUSICS + RestApiHelper.ID_PATH)
    public ResponseEntity<?> getMusicUrlById(@PathVariable(RestApiHelper.ID_VAR) long id) {
    	logger.info(StoreController.class.getName(), ".getMusicUrlById[id({})]", id);
    	
    	try {
    		Map<String, Object> result = new HashMap<>();
    		result = storeService.getMusicUrlById(id);
    		return new ResponseEntity<>(result, HttpStatus.OK);
    	} catch (Exception e) {
    		return RestApiHelper.generateErrorResponse(logger, e);
    	}
    }
    
}
