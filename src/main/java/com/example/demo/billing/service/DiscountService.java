package com.example.demo.billing.service;

import java.util.List;

import com.example.demo.billing.dto.AvailableDiscount;
import com.example.demo.pojo.Cart;

public interface DiscountService {

	/**
	 * Method calculates all available discounts based on the cart value
	 * 
	 * @param cart
	 * @return
	 */
	List<AvailableDiscount> calculateDiscount(Cart cart);

}
