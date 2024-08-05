package com.example.demo.billing.exceptions;

public class DiscountCalculationException extends RuntimeException {

	private static final long serialVersionUID = -5871073817093629682L;

	public DiscountCalculationException(String message) {
		super(message);
	}
	
}
