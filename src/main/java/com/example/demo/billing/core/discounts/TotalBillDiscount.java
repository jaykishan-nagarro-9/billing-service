package com.example.demo.billing.core.discounts;

import com.example.demo.billing.core.BillAmountDiscount;
import com.example.demo.pojo.Cart;

public class TotalBillDiscount extends BillAmountDiscount {

	public TotalBillDiscount(int groceryBillDiscount) {
		super(groceryBillDiscount, "Discount of %s for all category items on every 100 bill value");
	}

	@Override
	public double calculateDiscount(Cart cart) {
		
		double sum = cart.getCartItems().stream()
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
