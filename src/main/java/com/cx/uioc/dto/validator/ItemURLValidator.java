package com.cx.uioc.dto.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ItemURLValidator Class - Validator for URL in list
 */
public class ItemURLValidator implements ConstraintValidator<ItemURL, List<String>> {
	/**
	 * Methods
	 */
    /** Check up validation */
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    	if (values == null) {
    		return false;
    	}
    	
    	for (String value : values) {
    		try {
    			new URL(value);
    		} catch (MalformedURLException e) {
    			return false;
    		}
    	}
    	
        return true;
    }
}
