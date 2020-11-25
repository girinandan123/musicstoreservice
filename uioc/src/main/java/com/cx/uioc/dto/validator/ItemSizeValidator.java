package com.cx.uioc.dto.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * ItemSizeValidator Class - Validator for size in list
 */
public class ItemSizeValidator implements ConstraintValidator<ItemSize, List<String>> {
	/**
	 * Properties
	 */
	/** Maxima */
	private int max;
	
	/**
	 * Methods
	 */
	/** Initialization */
    @Override
    public void initialize(ItemSize constraint) {
    	this.max = constraint.max();
    }
 
    /** Check up validation */
    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
    	if (values == null) {
    		return false;
    	}
    	
    	for (String value : values) {
    		if (value.length() > max) {
    			return false;
    		}
    	}
    	
        return true;
    }
}
