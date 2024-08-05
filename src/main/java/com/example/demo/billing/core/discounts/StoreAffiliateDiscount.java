package com.example.demo.billing.core.discounts;

import com.example.demo.billing.core.PercentageDiscount;
import com.example.demo.enums.ProductCategory;
import com.example.demo.pojo.Cart;

public class StoreAffiliateDiscount extends PercentageDiscount {

	public StoreAffiliateDiscount(int storeAffiliateDiscount) {
		super(storeAffiliateDiscount, "Store affiliate discount %s percentage of total non-grocery product value");
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer().isStoreAffiliate()) {
			
			double sum = cart.getCartItems().stream()
					.filter(item -> !ProductCategory.GROCERY.equals(item.getProduct().getCategory()))
					.map(item -> item.getQuantity() * item.getProduct().getPrice())
					.mapToDouble(Double::valueOf)
					.sum();
			
			return percentageOfProductValue(sum, this.getValue());
		}
		
		return 0;
	}

}
