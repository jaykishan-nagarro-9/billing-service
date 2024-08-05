package com.example.demo.pojo;

import com.example.demo.enums.ProductCategory;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {

	private String name;
	private double price;
	private ProductCategory category;
	
}
