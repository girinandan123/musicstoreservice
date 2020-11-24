package com.cx.uioc.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cx.uioc.payload.request.LoginRequest;
import com.cx.uioc.payload.response.JwtResponse;
import com.cx.uioc.security.jwt.JwtUtils;
import com.cx.uioc.security.service.UserDetailsImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS}, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.ADMIN)
@Api(value = "AuthController", tags = "Auth Management")
public class AuthController {
	
	/**
	 * Properties
	 */
	/** Logger */
	private static final Logger logger = LogManager.getLogger(AuthController.class);
	
	/** Authentication Manager field */
	@Autowired
	private AuthenticationManager authenticationManager;

	/** JWT utility field */
	@Autowired	
	private JwtUtils jwtUtils;
	
	/** Messages */
    /** Success */
    private final static String msg_success = "Success | OK";
	
	/** Sign in path */
	public final static String SIGN_IN = "/signin";
	
	
	/** Sign in - only public URL */
	@ApiOperation(value = "Authenticate system user")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = msg_success, response = JwtResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized")
	})
	@PostMapping(value = SIGN_IN)
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		
		try {	
			
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserId(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();	
			List<String> roles = userDetails.getAuthorities().stream()
					.map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), 
					userDetails.getEmail(), roles));
		} catch(Exception e) {
			return RestApiHelper.generateErrorResponse(logger, e);
		}
		
	}
	
}
