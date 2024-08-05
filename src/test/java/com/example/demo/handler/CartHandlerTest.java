package com.example.demo.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.billing.dto.AvailableDiscount;
import com.example.demo.billing.exceptions.DiscountCalculationException;
import com.example.demo.billing.service.impl.DiscountServiceImpl;
import com.example.demo.enums.ProductCategory;
import com.example.demo.pojo.Cart;
import com.example.demo.pojo.CartItem;
import com.example.demo.pojo.Customer;
import com.example.demo.pojo.Product;

@SpringBootTest(classes = {CartHandler.class, DiscountServiceImpl.class})
class CartHandlerTest {

	private static final int YEAR = 365;
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
		
		Cart cart = createCart(Customer.builder()
				.name("Test user")
				.dob(LocalDate.now().minus(Period.ofDays(daysOfYear(18))))
				.registrationDate(LocalDate.now().minus(Period.ofDays(2*YEAR)))
				.isStoreAffiliate(true)
				.isStoreEmployee(false)
				.build());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(2, discounts.size());
		assertEquals(120, discounts.get(0).getDiscountAmount());
	}
	
	@Test
	void givenValidCartOfStoreEmployee_whenCalculateDiscount_thenSystemShouldProvideDiscountCalculation() {
		
		Cart cart = createCart2(Customer.builder()
				.name("Test Store user")
				.dob(LocalDate.now().minus(Period.ofDays(daysOfYear(18))))
				.registrationDate(LocalDate.now().minus(Period.ofDays(3*YEAR)))
				.isStoreAffiliate(false)
				.isStoreEmployee(true)
				.build());
		
		List<AvailableDiscount> discounts = cartHandler.calculateDiscount(cart);
		
		assertEquals(3, discounts.size());
		assertEquals(270, discounts.get(0).getDiscountAmount());
	}

	private int daysOfYear(int number) {
		return YEAR*number;
	}

	private Cart createCart(Customer customer) {
		
		Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(List.of(CartItem.builder()
        		.product(createProduct3Grocery())
        		.quantity(4)
        		.build()));
		
		return cart;
	}
	
	private Cart createCart2(Customer customer) {
		
		Cart cart = new Cart();
        cart.setCustomer(customer);
        cart.setCartItems(List.of(CartItem.builder().product(createProduct4Grocery()).quantity(5).build(),
        		CartItem.builder().product(createProduct1Bakery()).quantity(2).build()
        		));
		
		return cart;
	}

	private Product createProduct1Bakery() {
		return new Product("P1", 250, ProductCategory.BAKERY);
	}
	
	private Product createProduct2Bakery() {
		return new Product("P2", 50, ProductCategory.BAKERY);
	}
	
	private Product createProduct3Grocery() {
		return new Product("P3", 300, ProductCategory.GROCERY);
	}
	
	private Product createProduct4Grocery() {
		return new Product("P4", 80, ProductCategory.GROCERY);
	}
	
	
}
