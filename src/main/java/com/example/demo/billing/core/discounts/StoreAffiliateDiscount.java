package com.example.demo.billing.core.discounts;

import com.example.demo.billing.core.PercentageDiscount;
import com.example.demo.pojo.Cart;

public class StoreAffiliateDiscount extends PercentageDiscount {

	public StoreAffiliateDiscount(int storeAffiliateDiscount) {
		super(storeAffiliateDiscount, "Store affiliate discount %s percentage of total value");
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().isStoreAffiliate()) {
			
			double sum = cart.getCartItems().stream()
				.map(item -> item.getQuantity() * item.getProduct().getPrice())
				.mapToDouble(Double::valueOf)
				.sum();
			
			return percentageOfProductValue(sum, this.getValue());
		}
		
		return 0;
	}

}
