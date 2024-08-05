package com.example.demo.billing.service;

import java.util.List;

import com.example.demo.billing.core.Discountable;

public interface OpenDiscountService {

	List<Discountable> getPercentageDiscount();

	List<Discountable> getAdditionalDiscount();

}
