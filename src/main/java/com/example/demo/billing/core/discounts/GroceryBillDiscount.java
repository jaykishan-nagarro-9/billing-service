package com.example.demo.billing.core.discounts;

import com.example.demo.billing.core.PercentageDiscount;
import com.example.demo.enums.ProductCategory;
import com.example.demo.pojo.Cart;

public class GroceryBillDiscount extends PercentageDiscount {

	public GroceryBillDiscount(int groceryBillDiscount) {
		super(groceryBillDiscount, "Discount of %s for grocery category items for every 100 bill");
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
			return percentageOfProductValue(sum, this.getValue());
		}
		
		return 0;
	}

}
