package com.example.demo.billing.core;

import com.example.demo.enums.ProductCategory;
import com.example.demo.pojo.Cart;

public class GroceryBillDiscount implements Discountable {

	private int value;
	
	private String discount = String.format("Discount of %s for grocery category items", value);
	
	public GroceryBillDiscount(int groceryBillDiscount) {
		this.value = groceryBillDiscount;
	}

	@Override
	public String discountDescription() {
		return discount;
	}
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		double sum = cart.getCartItems().stream()
				.filter(item -> ProductCategory.GROCERY.equals(item.getProduct().getCategory()))
			.map(item -> item.getQuantity() * item.getProduct().getPrice())
			.mapToDouble(Double::valueOf)
			.sum();
		
		if(sum > 100) {
			sum = sum - (sum % 100);	//Value change from 945 to 900 as per discount rule
			return percentageOfProductValue(sum, value);
		}
		
		return 0;
	}

}
