package com.example.demo.pojo;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Customer {
	
	/**
	 * Name of the customer
	 */
	private String name;
	
	/**
	 * Date of birth of the customer
	 */
	private LocalDate dob;
	
	/**
	 * Customer registration date in system
	 */
	private LocalDate registrationDate;
	
	/**
	 * If store employee registered as customer 
	 */
	private boolean isStoreEmployee;
	
	/**
	 * If customer has purchased affiliate program.
	 */
	private boolean isStoreAffiliate;
	
	
}
