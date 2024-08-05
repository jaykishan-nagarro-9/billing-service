package com.example.demo.pojo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CartItem {

	private Product product;
	private int quantity;
	
}
