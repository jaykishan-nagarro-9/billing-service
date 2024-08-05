package com.example.demo.pojo;

import java.util.List;

import lombok.Data;

@Data
public class Cart {

	private List<CartItem> cartItems;
	private Customer customer;
	
}
