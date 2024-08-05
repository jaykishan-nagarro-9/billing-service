package com.example.demo.billing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.billing.core.Discountable;
import com.example.demo.billing.core.discounts.DedicatedCustomerDiscount;
import com.example.demo.billing.core.discounts.StoreAffiliateDiscount;
import com.example.demo.billing.core.discounts.StoreEmployeeDiscount;
import com.example.demo.billing.core.discounts.TotalBillDiscount;
import com.example.demo.billing.service.OpenDiscountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OpenDiscountServiceImpl implements OpenDiscountService {

	private final List<Discountable> percentageDiscounts = new ArrayList<Discountable>();
	private final List<Discountable> additionalDiscounts = new ArrayList<Discountable>();
	
	@Value("${billing.service.discount.store.employee}")
	private int storeEmployeeDiscount;
	
	@Value("${billing.service.discount.store.affiliate}")
	private int storeAffiliateDiscount;
	
	@Value("${billing.service.discount.dedicated.customer}")
	private int dedicatedCustomerDiscount;
	
	@Value("${billing.service.discount.total.bill}")
	private int totalBillDiscount;
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("Setup discounts");
		
		percentageDiscounts.add(new StoreEmployeeDiscount(storeEmployeeDiscount));
		percentageDiscounts.add(new StoreAffiliateDiscount(storeAffiliateDiscount));
		percentageDiscounts.add(new DedicatedCustomerDiscount(dedicatedCustomerDiscount));
		additionalDiscounts.add(new TotalBillDiscount(totalBillDiscount));
		
		log.info("Available discounts: {}", percentageDiscounts);
	}
	
	@Override
	public List<Discountable> getPercentageDiscount() {
		return percentageDiscounts;
	}
	
	@Override
	public List<Discountable> getAdditionalDiscount() {
		return additionalDiscounts;
	}
	
}
