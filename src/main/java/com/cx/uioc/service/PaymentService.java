package com.cx.uioc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@Service
@Transactional(rollbackFor = Exception.class)
public class PaymentService {
	
	/** Logger */
	private Logger logger = LogManager.getLogger(PaymentService.class);

	@Autowired
	private APIContext apiContext;
	
	public Map<String, Object> createPayment(Double price, String successUrl, String cancelUrl){
        
		logger.info(PaymentService.class.getName() + ".createPayment [price({}), successUrl({}), cancelUrl({})]", price, successUrl, cancelUrl);
		
		Map<String, Object> response = new HashMap<String, Object>();
        
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(price.toString());
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;
        try {
            String redirectUrl = "";            
            createdPayment = payment.create(apiContext);
            if (createdPayment != null) {
        		
            	logger.info(PaymentService.class.getName() + ".createPayment [createdPayment-Id({})]", createdPayment.getId());

                List<Links> links = createdPayment.getLinks();
                for (Links link : links) {
                    if (link.getRel().equals("approval_url")) {
                        redirectUrl = link.getHref();
                        break;
                    }
                }
            	logger.info(PaymentService.class.getName() + ".createPayment [redirectUrl({})]", redirectUrl);

                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Error happened during payment creation!");
        }
        return response;
    }
	
	public Map<String, Object> completePayment(String paymentId, String PayerId){
        
    	logger.info(PaymentService.class.getName() + ".completePayment [paymentId({}), PayerId({})", paymentId, PayerId);
		
		Map<String, Object> response = new HashMap<>();
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(PayerId);
        try {            
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            if (createdPayment != null) {
            	
            	logger.info(PaymentService.class.getName() + ".completePayment [createdPayment-Id({})", createdPayment.getId());

                response.put("status", "success");
                response.put("payment", createdPayment);
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }
        return response;
    }
}
