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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cx.uioc.dto.SystemUserDTO;
import com.cx.uioc.exception.ValidationFailException;
import com.cx.uioc.service.SystemUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * SystemUserController Class - Controller for system user management (version 2.x)
 */
@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS }, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN)
@Api(value = "SystemUserController", tags = "Stuff Management")
public class SystemUserController {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(SystemUserController.class);
	
	/**
	 * Constants
	 */
	/** Paths */
	/** Path of the users */
	public final static String USERS = "/users";
	/** Path of the passwords */
	public final static String PASSWORDS_PATH = "/passwords";
	
	
	/** Messages */
    /** Success */
    private final static String msg_success = "Success | OK";

	/**
	 * Members
	 */
	/** Service for system user management */
	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * Methods
	 */
	/**
	 * Get system user by identifier
	 */
//	@ApiOperation(value = "Get the employee by id")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = msg_success, response = SystemUserDTO.class),
//			@ApiResponse(code = 404, message = "Not found the employee specified by id", response = String.class)
//	})
//	@GetMapping(value = SYSTEM_USERS_PATH + RestApiHelper.ID_PATH)
//	public ResponseEntity<?> getSystemUserById(@PathVariable(RestApiHelper.ID_VAR) long id) {
//		logger.info(SystemUserController.class.getName() + ".getSystemUserById [id({})]", id);
//		
//		try {
//			// Get the system user by identifier in service
//			SystemUserDTO systemUser = systemUserService.getSystemUserById(id);
//			return new ResponseEntity<SystemUserDTO>(systemUser, HttpStatus.OK);
//		} catch (Exception e) {
//        	return RestApiHelper.generateErrorResponse(logger, e);
//        }
//	}
	
	/**
	 * Get System user by name
	 */
//	@ApiOperation(value = "Get the employee by name")
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = msg_success, response = SystemUserDTO.class),
//			@ApiResponse(code = 404, message = "Not found the employee specified by name", response = String.class)
//	})
//	@GetMapping(value = SYSTEM_USERS_PATH)
//	public ResponseEntity<?> getSystemUserByName(@RequestParam(value = RestApiHelper.NAME_PARAM) String username) {
//		
//		SystemUserDTO user = null;
//		
//		try {
//			user = systemUserService.getSystemUserByName(username);
//			return new ResponseEntity<SystemUserDTO>(user, HttpStatus.OK);
//		} catch (Exception e) {
//			return RestApiHelper.generateErrorResponse(logger, e);
//		}	
//	}
	
	/**
	 * Evaluate user password is correct
	 */
//	@ApiOperation(value = "Check if the password correct")
//    @ApiResponses(value = { 
//            @ApiResponse(code = 200, message = msg_success, response = Boolean.class)
//    })
//	@GetMapping(value = PASSWORDS_PATH)
//	public ResponseEntity<?> isPasswordCorrect(@RequestParam(value = RestApiHelper.ID_VAR) long id, @RequestParam(value = RestApiHelper.PASSWORD_VAR) String password) {
//		logger.info(SystemUserController.class.getName() + ".isPasswordCorrect [id({}), password({})]", id, password);
//		
//		try {
//			boolean result = systemUserService.isPasswordCorrect(id, password);
//			return new ResponseEntity<Boolean>(result, HttpStatus.OK);
//		} catch (Exception e) {
//			return RestApiHelper.generateErrorResponse(logger, e);
//		}
//	}
	
	/**
	 * Add a new stuff - second administrator
	 */
	@ApiOperation(value = "Add a new system user")
    @ApiResponses(value = { 
            @ApiResponse(code = 201, message = "New stuff created", response = Void.class),
            @ApiResponse(code = 400, message = "Invalid the stuff information to add", response = String.class),
            @ApiResponse(code = 409, message = "Already existed the same stuff", response = String.class),
    })
	@PostMapping(value = USERS)
	public ResponseEntity<?> addNewSystemUser(@Valid @RequestBody SystemUserDTO systemUser, BindingResult bindingResult, UriComponentsBuilder ucBuilder) {
		logger.info(SystemUserController.class.getName() + ".addNewSystemUser [systemUser({})]", systemUser);
		
        try {
	        // Check up the validations
	        if (bindingResult.hasFieldErrors()) {
	        	if (!bindingResult.hasFieldErrors("systemUser")) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);
	        	}
	        	if (bindingResult.getFieldErrorCount() > NumberUtils.INTEGER_ONE) {
	        		String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
	        		throw new ValidationFailException(errString);	        		
	        	}
	        }
	        
            // Add a new system user by services
            String addedId = systemUserService.addNewSystemUser(systemUser);
        	
            // Prepare the response header
            HttpHeaders headers = new HttpHeaders();
            headers.setAccessControlExposeHeaders(WebCorsHelper.EXPOSE_HEADERS);
            headers.setLocation(ucBuilder.path(RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN + USERS + RestApiHelper.ID_PATH).buildAndExpand(addedId).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
	
	/**
	 * Update the system user by identifier
	 */
	@ApiOperation(value = "Update the system user by id")
    @ApiResponses(value = { 
            @ApiResponse(code = 200, message = msg_success, response = SystemUserDTO.class),
            @ApiResponse(code = 400, message = "Invalid system user information to update the user", response = String.class),
            @ApiResponse(code = 404, message = "Not found the system user specified by user's identifier", response = String.class)
    })
	@PutMapping(value = USERS + RestApiHelper.ID_PATH)
	public ResponseEntity<?> updateSystemUser(@Valid @PathVariable(RestApiHelper.ID_VAR) String userId, @Valid @RequestBody SystemUserDTO systemUser, BindingResult bindingResult) {
		logger.info(SystemUserController.class.getName() + ".updateSystemUser [userId({}), systemUser({})]", userId, systemUser);

		try {
			// Check up the validations
			if (bindingResult.hasFieldErrors()) {
				String errString = bindingResult.getAllErrors().get(0).getDefaultMessage();
				throw new ValidationFailException(errString);
			}
			
			// Update the system user by identifier in service
			SystemUserDTO updated = systemUserService.updateSystemUser(userId, systemUser);
			return new ResponseEntity<SystemUserDTO>(updated, HttpStatus.OK);
		
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
	
	/**
     * Delete the system user by identifier
     */
    @ApiOperation(value = "Delete the system user by identifier")
    @ApiResponses(value = { 
            @ApiResponse(code = 204, message = msg_success, response = Void.class),
            @ApiResponse(code = 404, message = "Not found the system user specified by identifier to delete", response = String.class)
    })
    @DeleteMapping(value = USERS + RestApiHelper.ID_PATH)
    public ResponseEntity<?> deleteSystemUser(@PathVariable(RestApiHelper.ID_VAR) String userId) {
        logger.info(SystemUserController.class.getName() + ".deleteSystemUser [userId({})]", userId);

        try {
            // Delete the system user by service
            systemUserService.deleteSystemUser(userId);
        	return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
    }
    
    /**
     * Get all system users
     */
    @ApiOperation(value = "Get all system users")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = msg_success, response = SystemUserDTO.class, responseContainer = "List"),
			
	})
    @GetMapping(value = USERS)
	public ResponseEntity<?> getSystemUsers() {
		logger.info(SystemUserController.class.getName() + ".getSystemUsers [({})]");
		
		try {
			// Get the employees by department identifier in service
			List<SystemUserDTO> systemUsers = systemUserService.getSystemUsers();
			return new ResponseEntity<List<SystemUserDTO>>(systemUsers, HttpStatus.OK);
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
}

