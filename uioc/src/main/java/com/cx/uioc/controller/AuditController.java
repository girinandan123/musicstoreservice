package com.cx.uioc.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cx.uioc.dto.AuditDTO;
import com.cx.uioc.dto.MusicDTO;
import com.cx.uioc.exception.ValidationFailException;
import com.cx.uioc.service.AuditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import static com.cx.uioc.controller.MusicController.MUSICS;

import java.util.List;

@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS }, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.AUDIT + MUSICS)
@Api(value = "AuditController", tags = "Audit Management")
public class AuditController {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(AuditController.class);
	
	/**
	 * Constants
	 */
	/** Paths */
	/** Path of passed */
	public final static String PASSED = "/passed";
	/** Path of rejected */
	public final static String REJECTED = "/rejected";
	/** Messages */
    /** Success */
    private final static String msg_success = "Success | OK";	
	
	/**
	 * Members
	 */
	/** Service for audit management */
	@Autowired
	private AuditService auditService;
	
	/**
	 * Methods
	 */
	
	/**
	 * Set the music audit status to passed
	 */
	@ApiOperation(value = "Set the audit status to passed")
    @ApiResponses(value = {             
            @ApiResponse(code = 404, message = "Not found the music specified by id", response = String.class)
    })
	@PostMapping(value = PASSED)
	public ResponseEntity<?> setAuditToPassed(@Valid @RequestBody AuditDTO audit, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		logger.info(AuditController.class.getName() + ".setAuditToPassed [AuditDTO({})]", audit);
		
        try {
	        // Check up the validations
	        if (bindingResult.hasFieldErrors()) {
	        	if (!bindingResult.hasFieldErrors("audit")) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);
	        	}
	        	if (bindingResult.getFieldErrorCount() > NumberUtils.INTEGER_ONE) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);	        		
	        	}
	        }
	        
            // Set the audit status to passed by service
            long setId = auditService.setAuditToPassed(audit);
        	
            // Prepare the response header
            HttpHeaders headers = new HttpHeaders();
            headers.setAccessControlExposeHeaders(WebCorsHelper.EXPOSE_HEADERS);
            headers.setLocation(ucBuilder.path(RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN + MUSICS + RestApiHelper.ID_PATH).buildAndExpand(setId).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
	
	/**
	 * Set the music audit status to rejected
	 */
	@ApiOperation(value = "Set the audit status to rejected")
    @ApiResponses(value = {             
            @ApiResponse(code = 404, message = "Not found the music specified by id", response = String.class)
    })
	@PostMapping(value = REJECTED)
	public ResponseEntity<?> setAuditToRejected(@Valid @RequestBody AuditDTO audit, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		logger.info(AuditController.class.getName() + ".setAuditToPassed [AuditDTO({})]", audit);
		
        try {
	        // Check up the validations
	        if (bindingResult.hasFieldErrors()) {
	        	if (!bindingResult.hasFieldErrors("audit")) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);
	        	}
	        	if (bindingResult.getFieldErrorCount() > NumberUtils.INTEGER_ONE) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);	        		
	        	}
	        }
	        
            // Set the audit status to passed by service
            long setId = auditService.setAuditToRejected(audit);
        	
            // Prepare the response header
            HttpHeaders headers = new HttpHeaders();
            headers.setAccessControlExposeHeaders(WebCorsHelper.EXPOSE_HEADERS);
            headers.setLocation(ucBuilder.path(RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN + MUSICS + RestApiHelper.ID_PATH).buildAndExpand(setId).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.OK);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
	
	/**
	 * Update the system user by identifier
	 */
//	@ApiOperation(value = "Update the system user by id")
//    @ApiResponses(value = { 
//            @ApiResponse(code = 200, message = msg_success, response = SystemUserDTO.class),
//            @ApiResponse(code = 400, message = "Invalid system user information to update the user", response = String.class),
//            @ApiResponse(code = 404, message = "Not found the system user specified by user's identifier", response = String.class)
//    })
//	@PutMapping(value = USERS + RestApiHelper.ID_PATH)
//	public ResponseEntity<?> updateSystemUser(@PathVariable(RestApiHelper.ID_VAR) long userId, @Valid @RequestBody SystemUserDTO systemUser, BindingResult bindingResult) {
//		logger.info(SystemUserController.class.getName() + ".updateSystemUser [userId({}), systemUser({})]", userId, systemUser);
//
//		try {
//			// Check up the validations
//			if (bindingResult.hasFieldErrors()) {
//				String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
//				throw new ValidationFailException(errString);
//			}
//			
//			// Update the system user by identifier in service
//			SystemUserDTO updated = systemUserService.updateSystemUser(userId, systemUser);
//			return new ResponseEntity<SystemUserDTO>(updated, HttpStatus.OK);
//		} catch (Exception e) {
//        	return RestApiHelper.generateErrorResponse(logger, e);
//        }
//	}
	
	/**
     * Delete the music by identifier
     */
//    @ApiOperation(value = "Delete the music by identifier")
//    @ApiResponses(value = { 
//            @ApiResponse(code = 204, message = msg_success, response = Void.class),
//            @ApiResponse(code = 404, message = "Not found the music specified by identifier to delete", response = String.class)
//    })
//    @DeleteMapping(value = MUSICS + RestApiHelper.ID_PATH)
//    public ResponseEntity<?> deleteMusic(@PathVariable(RestApiHelper.ID_VAR) long id) {
//        logger.info(MusicController.class.getName() + ".deleteMusic [id({})]", id);
//
//        try {
//            // Delete the music by service
//            musicService.deleteMusic(id);
//        	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//        	return RestApiHelper.generateErrorResponse(logger, e);
//        }
//    }
    
    /**
     * Get all musics where the audit status is 'pending'
     */
    @ApiOperation(value = "Get all musics where audit status is pending")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = msg_success, response = MusicDTO.class, responseContainer = "List"),
			
	})
    @GetMapping
	public ResponseEntity<?> getMusics() {
		logger.info(AuditController.class.getName() + ".getMusics [({})]");
		
		try {
			// Get all musics where the audit status is pending
			List<MusicDTO> dtos = auditService.getPendingMusics();
			return new ResponseEntity<List<MusicDTO>>(dtos, HttpStatus.OK);
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
}
