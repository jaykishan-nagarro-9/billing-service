package com.example.demo.handler;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import com.example.demo.enums.ProductCategory;
import com.example.demo.pojo.CartItem;
import com.example.demo.pojo.Customer;
import com.example.demo.pojo.Product;

public interface CartValuesProvider {

	public static final int YEAR_DAYS = 365;
	
	default Customer customerNotAffiliateEmployeeExisting() {
		return Customer.builder()
				.name("Test New user")
				.dob(LocalDate.now().minus(Period.ofDays(18*YEAR_DAYS)))
				.registrationDate(LocalDate.now().minus(Period.ofDays(1*YEAR_DAYS)))
				.isStoreAffiliate(false)
				.isStoreEmployee(false)
				.build();
	}
	
	default Customer customerExistingAffiliatedNotEmployed() {
		return Customer.builder()
				.name("Test Affiliate user")
				.dob(LocalDate.now().minus(Period.ofDays(18*YEAR_DAYS)))
				.registrationDate(LocalDate.now().minus(Period.ofDays(2*YEAR_DAYS)))
				.isStoreAffiliate(true)
				.isStoreEmployee(false)
				.build();
	}
	
	default Customer customerEmployeeExistingNotAffiliated() {
		return Customer.builder()
				.name("Test Store user")
				.dob(LocalDate.now().minus(Period.ofDays(18*YEAR_DAYS)))
				.registrationDate(LocalDate.now().minus(Period.ofDays(3*YEAR_DAYS)))
				.isStoreAffiliate(false)
				.isStoreEmployee(true)
				.build();
	}
	
	default List<CartItem> cartItems1() {
		return List.of(CartItem.builder()
        		.product(createProduct3Grocery())
        		.quantity(4)
        		.build());
	}
	
	default List<CartItem> cartItems2() {
		return List.of(CartItem.builder().product(createProduct4Grocery()).quantity(6).build(),
        		CartItem.builder().product(createProduct1Bakery()).quantity(2).build()
        		);
	}
	
	default List<CartItem> cartItems3() {
		return List.of(CartItem.builder().product(createProduct4Grocery()).quantity(3).build(),
        		CartItem.builder().product(createProduct2Bakery()).quantity(4).build()
        		);
	}
	
	default List<CartItem> cartItemsForBakery() {
		return List.of(CartItem.builder().product(createProduct1Bakery()).quantity(3).build(),
        		CartItem.builder().product(createProduct2Bakery()).quantity(4).build()
        		);
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
