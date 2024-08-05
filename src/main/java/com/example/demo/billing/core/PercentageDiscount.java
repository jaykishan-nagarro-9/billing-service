package com.example.demo.billing.core;

public abstract class PercentageDiscount implements Discountable {

	private int value;

	private String discount;
	
	public PercentageDiscount(int percentageDiscount, String descriptionFormat) {
		this.value = percentageDiscount;
		this.discount = String.format(descriptionFormat, value);
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	public String discountDescription() {
		return discount;
	}
	
}
