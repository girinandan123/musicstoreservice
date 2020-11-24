package com.cx.uioc.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cx.uioc.dto.MusicDTO;
import com.cx.uioc.exception.ValidationFailException;
import com.cx.uioc.service.MusicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS }, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN)
@Api(value = "MusicController", tags = "Music Management")
public class MusicController {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(MusicController.class);
	
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
	private MusicService musicService;
	
	/**
	 * Methods
	 */
	
	/**
	 * Add a new music
	 */
	@ApiOperation(value = "Add a new music")
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "New music created", response = Void.class),
            @ApiResponse(code = 400, message = "Invalid the music information to add", response = String.class),
            @ApiResponse(code = 409, message = "Already existed the same music", response = String.class)
    })
	@PostMapping(value = MUSICS)
	public ResponseEntity<?> addNewMusic(@Valid @RequestBody MusicDTO music, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		logger.info(MusicController.class.getName() + ".addNewMusic [music({})]", music);
		
        try {
	        // Check up the validations
	        if (bindingResult.hasFieldErrors()) {
	        	if (!bindingResult.hasFieldErrors("music")) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);
	        	}
	        	if (bindingResult.getFieldErrorCount() > NumberUtils.INTEGER_ONE) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);	        		
	        	}
	        }
	        
            // Add a new system user by services
            long addedId = musicService.addNewMusic(music);
        	
            // Prepare the response header
            HttpHeaders headers = new HttpHeaders();
            headers.setAccessControlExposeHeaders(WebCorsHelper.EXPOSE_HEADERS);
            headers.setLocation(ucBuilder.path(RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN + MUSICS + RestApiHelper.ID_PATH).buildAndExpand(addedId).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
	
	
	/**
     * Delete the music by identifier
     */
    @ApiOperation(value = "Delete the music by identifier")
    @ApiResponses(value = { 
            @ApiResponse(code = 204, message = msg_success, response = Void.class),
            @ApiResponse(code = 404, message = "Not found the music specified by identifier to delete", response = String.class)
    })
    @DeleteMapping(value = MUSICS + RestApiHelper.ID_PATH)
    public ResponseEntity<?> deleteMusic(@PathVariable(RestApiHelper.ID_VAR) long id) {
        logger.info(MusicController.class.getName() + ".deleteMusic [id({})]", id);

        try {
            // Delete the music by service
            musicService.deleteMusic(id);
        	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
    }
    
    /**
     * Get all musics
     */
    @ApiOperation(value = "Get all musics")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = msg_success, response = MusicDTO.class, responseContainer = "List"),
			
	})
    @GetMapping(value = MUSICS)
	public ResponseEntity<?> getMusics() {
		logger.info(MusicController.class.getName() + ".getMusics [({})]");
		
		try {
			// Get all musics
			List<MusicDTO> dtos = musicService.getMusics();
			return new ResponseEntity<List<MusicDTO>>(dtos, HttpStatus.OK);
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
}
