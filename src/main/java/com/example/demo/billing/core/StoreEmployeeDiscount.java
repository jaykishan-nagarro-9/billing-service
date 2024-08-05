package com.example.demo.billing.core;

import com.example.demo.pojo.Cart;

public class StoreEmployeeDiscount implements Discountable {

	private int value;
	
	private String discount = String.format("Store emplyee discount %s percentage of total value", value);
	
	public StoreEmployeeDiscount(int storeEmployeeDiscount) {
		this.value = storeEmployeeDiscount;
	}

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
