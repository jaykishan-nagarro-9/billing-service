package com.example.demo.billing.core.discounts;

import java.time.LocalDate;
import java.time.Period;

import com.example.demo.billing.core.PercentageDiscount;
import com.example.demo.pojo.Cart;

public class DedicatedCustomerDiscount extends PercentageDiscount {

	private static final int YEAR = 2;
	
	public DedicatedCustomerDiscount(int dedicatedCustomerDiscount) {
		super(dedicatedCustomerDiscount, "Existing customer (more than " + YEAR + " years) gets %s discount");
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().getRegistrationDate().isBefore(LocalDate.now().minus(Period.ofDays(YEAR*365)))) {
			double sum = cart.getCartItems().stream()
				.map(item -> item.getQuantity() * item.getProduct().getPrice())
				.mapToDouble(Double::valueOf)
				.sum();
			return percentageOfProductValue(sum, this.getValue());
		}
		
		return 0;
	}

}
