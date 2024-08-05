package com.example.demo.billing.dto;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@ToString
@Value
@Builder
public class AvailableDiscount {

	private double discountAmount;
	private String discountDetail;
	
}
