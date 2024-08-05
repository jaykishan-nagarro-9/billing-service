package com.example.demo.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.billing.dto.AvailableDiscount;
import com.example.demo.billing.exceptions.DiscountCalculationException;
import com.example.demo.billing.service.impl.DiscountServiceImpl;
import com.example.demo.pojo.Cart;
import com.example.demo.pojo.CartItem;
import com.example.demo.pojo.Customer;

@SpringBootTest(classes = {CartHandler.class, DiscountServiceImpl.class})
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
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(2, discounts.size());
		assertEquals(120, discounts.get(0).getDiscountAmount());
	}

	@Test
	void givenValidCartOfStoreEmployee_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerEmployeeExistingNotAffiliated(), cartItems2());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(3, discounts.size());
		assertEquals(270, discounts.get(0).getDiscountAmount());
	}

	
	
	@Test
	void givenValidCartForRegularCustomer_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerNotAffiliateEmployeeExisting(), cartItems3());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(1, discounts.size());
		assertEquals(10, discounts.get(0).getDiscountAmount());
	}
	
	@Test
	void givenValidCartForNotSpecialCustomer_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerNotAffiliateEmployeeExisting(), cartItems2());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(1, discounts.size());
		assertEquals(20, discounts.get(0).getDiscountAmount());
	}
	
	@Test
	void givenValidCartForBakeryProducts_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart(customerEmployeeExistingNotAffiliated(), cartItemsForBakery());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(2, discounts.size());
		assertEquals(285, discounts.get(0).getDiscountAmount());
	}

	private Cart createCart(Customer customer, List<CartItem> cartItems) {
		
		Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(cartItems);
		
		return cart;
	}

}
