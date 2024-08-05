package com.example.demo.billing.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.demo.pojo.Cart;

@Component
public class StoreEmployeeDiscount implements Discountable {

	/**
	 * Percentage value of discount
	 */
	@Value("${billing.service.discount.store.employee}")
	private int value;
	
	private String discount = String.format("Store emplyee discount %s percentage of total value", value);
	
	@Override
	public String discountDescription() {
		return discount;
	}

	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().isStoreEmployee()) {
			double sum = cart.getCartItems().stream()
				.map(item -> item.getQuantity() * item.getProduct().getPrice())
				.mapToDouble(Double::valueOf)
				.sum();
			return percentageOfProductValue(sum, value);
		}
		
		return 0;
	}

}
