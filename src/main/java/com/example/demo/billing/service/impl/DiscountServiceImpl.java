package com.example.demo.billing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.billing.core.DedicatedCustomerDiscount;
import com.example.demo.billing.core.Discountable;
import com.example.demo.billing.core.StoreAffiliateDiscount;
import com.example.demo.billing.core.StoreEmployeeDiscount;
import com.example.demo.billing.service.DiscountService;
import com.example.demo.pojo.Cart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DiscountServiceImpl implements DiscountService {

	private final List<Discountable> discountables = new ArrayList<Discountable>();
	
	/**
	 * Percentage value of discount
	 */
	@Value("${billing.service.discount.store.employee}")
	private int storeEmployeeDiscount;
	
	@Value("${billing.service.discount.store.affiliate}")
	private int storeAffiliateDiscount;
	
	@Value("${billing.service.discount.dedicated.customer}")
	private int dedicatedCustomerDiscount;
	
	@Value("${billing.service.discount.grocery.bill}")
	private int groceryBillDiscount;
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		log.info("Setup discounts");
		
		discountables.add(new StoreEmployeeDiscount(storeEmployeeDiscount));
		discountables.add(new StoreAffiliateDiscount(storeAffiliateDiscount));
		discountables.add(new DedicatedCustomerDiscount(dedicatedCustomerDiscount));
		
		log.info("Available discounts: {}", discountables);
	}
	
	@Override
	public String calculateDiscount(Cart cart) {
		return null;
	}

}
