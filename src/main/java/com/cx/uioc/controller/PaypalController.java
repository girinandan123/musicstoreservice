package com.cx.uioc.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cx.uioc.service.PaymentService;
import io.swagger.annotations.Api;

@RestController
@CrossOrigin(origins = {WebCorsHelper.ORIGINS }, maxAge = WebCorsHelper.MAX_AGE, allowCredentials = WebCorsHelper.ALLOW_CREDENTIALS)
@RequestMapping(value = RestApiHelper.SERVICE_PATH + RestApiHelper.API_VER1 + RestApiHelper.PAYPAL)
@Api(value = "PaypalController", tags = "Paypal usage")
public class PaypalController {
	/** Logger */
	private static final Logger logger = LogManager.getLogger(PaypalController.class);
	
	/**
	 * Constants
	 */
	/** Paths */
//	/** Path of the music */
//	public final static String MUSICS = "/musics";
//	/** Path of the passwords */
//	public final static String PASSWORDS_PATH = "/passwords";
	
	
	/** Messages */
    /** Success */
    private final static String msg_success = "Success | OK";

	/**
	 * Members
	 */
	/** Service for payment */
	@Autowired
	private PaymentService paymentService;
		
	/**
	 * Methods
	 */
    
    /**
     * Make payments
     */    
    @PostMapping(value = "/make/payment")
	public ResponseEntity<?> makePayment(@Valid @RequestParam("price") Double price, 
			@Valid @RequestParam("success_url") String successUrl,
			@Valid @RequestParam("cancel_url") String cancelUrl) {
		
    	logger.info(PaypalController.class.getName() + ".makePayment [price({}), successUrl({}), cancelUrl({})]", price, successUrl, cancelUrl);
		
		try {
			Map<String, Object> result = new HashMap<>();
			result = paymentService.createPayment(price, successUrl, cancelUrl);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
	}
    
    /**
     * Execute payments
     */
    @PostMapping(value = "/complete/payment")
    public ResponseEntity<?> completePayment(@Valid @RequestParam("paymentId") String paymentId, @Valid @RequestParam("PayerId") String PayerId){
        
    	logger.info(PaypalController.class.getName() + ".completePayment [paymentId({}), PayerId({})]", paymentId, PayerId);
    	
    	try {
        	Map<String, Object> result = new HashMap<>();
        	result = paymentService.completePayment(paymentId, PayerId);
        	return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
        	return RestApiHelper.generateErrorResponse(logger, e);
        }
    }
}
