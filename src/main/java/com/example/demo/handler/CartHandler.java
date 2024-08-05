package com.example.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.example.demo.billing.exceptions.DiscountCalculationException;
import com.example.demo.billing.service.DiscountService;
import com.example.demo.pojo.Cart;
import com.example.demo.pojo.CartItem;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CartHandler {

	@Autowired
	private DiscountService discountService;
	
	public double calculateDiscount(Cart cart) {
		
		if(cart.getCustomer() == null)
			throw new DiscountCalculationException("Missing customer details");
		
		if(CollectionUtils.isEmpty(cart.getCartItems()))
			throw new DiscountCalculationException("No items available in the cart");
		
		log.info("Cart: {}", cart);
		prettyPrintCart(cart);

		return discountService.calculateDiscount(cart);
	}

	/**
	 * This method is to display cart items properly
	 * @param cart
	 */
	private void prettyPrintCart(Cart cart) {
		
		System.out.println("=============================================");
		System.out.println("Customer: " + cart.getCustomer().getName());
		System.out.println("Cart items: \n-------------------");
		double sum = 0;
		double val = 0;
		for (CartItem item : cart.getCartItems()) {
			val = item.getQuantity() * item.getProduct().getPrice();
			System.out.println(String.format("%s [%s]: %s x %s = %s", item.getProduct().getName(), item.getProduct().getCategory(), item.getQuantity(), item.getProduct().getPrice(), val));
			sum += val;
		}
		
		System.out.println(String.format("-------------\nTotal: %s", sum));
		
	}
	
}
