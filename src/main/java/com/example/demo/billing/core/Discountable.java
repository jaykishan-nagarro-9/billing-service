package com.example.demo.billing.core;

import com.example.demo.pojo.Cart;

/**
 * This interface helps designing discount configuration in the system. <br>
 * It has some common methods that can be used to calculate discount.
 */
public interface Discountable {

	/**
	 * Calculate discount based on configuration
	 * @return
	 */
	public double calculateDiscount(Cart cart);
	
	/**
	 * Show case custom product description
	 * @return discount description
	 */
	public String discountDescription();
	
}
