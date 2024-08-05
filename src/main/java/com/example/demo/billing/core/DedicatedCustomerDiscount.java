package com.example.demo.billing.core;

import java.time.Duration;
import java.time.LocalDate;

import com.example.demo.pojo.Cart;

public class DedicatedCustomerDiscount implements Discountable {

	private static final int YEAR = 2;
	
	private int value;
	
	private String discount = String.format("Existing customer (more than %s years) gets %s discount", YEAR, value);
	
	public DedicatedCustomerDiscount(int dedicatedCustomerDiscount) {
		this.value = dedicatedCustomerDiscount;
	}

	@Override
	public String discountDescription() {
		return discount;
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().getRegistrationDate().isBefore(LocalDate.now().minus(Duration.ofDays(YEAR*365)))) {
			double sum = cart.getCartItems().stream()
				.map(item -> item.getQuantity() * item.getProduct().getPrice())
				.mapToDouble(Double::valueOf)
				.sum();
			return percentageOfProductValue(sum, value);
		}
		
		return 0;
	}

}
