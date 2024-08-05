package com.example.demo.billing.core;

import com.example.demo.pojo.Cart;

public class StoreAffiliateDiscount implements Discountable {

	private int value;
	
	private String discount = String.format("Store affiliate discount %s percentage of total value", value);
	
	public StoreAffiliateDiscount(int storeAffiliateDiscount) {
		this.value = storeAffiliateDiscount;
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().isStoreAffiliate()) {
			double sum = cart.getCartItems().stream()
				.map(item -> item.getQuantity() * item.getProduct().getPrice())
				.mapToDouble(Double::valueOf)
				.sum();
			return percentageOfProductValue(sum, value);
		}
		
		return 0;
	}

	@Override
	public String discountDescription() {
		return discount;
	}

}
