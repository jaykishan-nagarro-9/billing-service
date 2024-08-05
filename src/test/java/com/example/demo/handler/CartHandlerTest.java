package com.example.demo.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.billing.exceptions.DiscountCalculationException;
import com.example.demo.pojo.Cart;
import com.example.demo.pojo.CartItem;
import com.example.demo.pojo.Customer;

@SpringBootTest
class CartHandlerTest implements CartValuesProvider {

	@Autowired
	private CartHandler cartHandler;
	
	@Test
	void givenEmptyCartList_whenCalculateDiscount_thenSystemShouldThrowException() {
		
		Cart cart = new Cart();
		cart.setCustomer(Customer.builder().build());
		
		DiscountCalculationException exception = assertThrows(
                DiscountCalculationException.class,
                () -> cartHandler.calculateDiscount(cart)
        );
        assertEquals("No items available in the cart", exception.getMessage());
	}
	
	@Test
	void givenCartWithMissingCustomer_whenCalculateDiscount_thenSystemShouldThrowException() {
		
		Cart cart = new Cart();
		
		DiscountCalculationException exception = assertThrows(
                DiscountCalculationException.class,
                () -> cartHandler.calculateDiscount(cart)
        );
        assertEquals("Missing customer details", exception.getMessage());
	}
	

	@Test
	void givenValidCart_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerExistingAffiliatedNotEmployed(), cartItems1());
		
		assertEquals(60, cartHandler.calculateDiscount(cart));
	}

	@Test
	void givenValidCartOfStoreEmployee_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerEmployeeExistingNotAffiliated(), cartItems2());
		
		assertEquals(195, cartHandler.calculateDiscount(cart));
	}

	
	
	@Test
	void givenValidCartForRegularCustomer_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerNotAffiliateEmployeeExisting(), cartItems3());
		
		assertEquals(20, cartHandler.calculateDiscount(cart));
	}
	
	@Test
	void givenValidCartForNotSpecialCustomer_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerNotAffiliateEmployeeExisting(), cartItems2());
		
		assertEquals(45, cartHandler.calculateDiscount(cart));
	}
	
	@Test
	void givenValidCartForBakeryProducts_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerEmployeeExistingNotAffiliated(), cartItemsForBakery());
		
		assertEquals(330, cartHandler.calculateDiscount(cart));
	}
	
	@Test
	void givenValidCartForAffiliatedCustomer_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerExistingAffiliatedNotEmployed(), cartItems2());
		
		assertEquals(95, cartHandler.calculateDiscount(cart));
	}

	private Cart createCart(Customer customer, List<CartItem> cartItems) {
		
		Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(cartItems);
		
		return cart;
	}

}
