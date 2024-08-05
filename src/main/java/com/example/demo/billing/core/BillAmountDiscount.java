package com.example.demo.billing.core;

public abstract class BillAmountDiscount implements Discountable {

	private int value;

	private String discount;
	
	public BillAmountDiscount(int percentageDiscount, String descriptionFormat) {
		this.value = percentageDiscount;
		this.discount = String.format(descriptionFormat, value);
	}
	
	public int getValue() {
		return value;
	}
	
	/**
	 * Calculates percentage value
	 * 
	 * @param productPrice
	 * @param discount
	 * @return
	 */
	protected double percentageOfProductValue(double productPrice, int discount) {
		return (productPrice * discount)/100;
	}
	
	@Override
	public String discountDescription() {
		return discount;
	}
	
}
