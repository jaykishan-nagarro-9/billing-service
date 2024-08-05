package com.example.demo.billing.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.example.demo.billing.core.Discountable;
import com.example.demo.billing.core.discounts.DedicatedCustomerDiscount;
import com.example.demo.billing.core.discounts.GroceryBillDiscount;
import com.example.demo.billing.core.discounts.StoreAffiliateDiscount;
import com.example.demo.billing.core.discounts.StoreEmployeeDiscount;
import com.example.demo.billing.dto.AvailableDiscount;
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
		discountables.add(new GroceryBillDiscount(groceryBillDiscount));
		
		log.info("Available discounts: {}", discountables);
	}
	
	@Override
	public List<AvailableDiscount> calculateDiscount(Cart cart) {
		
		return discountables.parallelStream()
				.map(discount -> {
					return AvailableDiscount.builder()
							.discountAmount(discount.calculateDiscount(cart))
							.discountDetail(discount.discountDescription())
							.build();
				})
				.filter(d -> d.getDiscountAmount() > 0)
				.sorted((d1, d2) -> Double.compare(d2.getDiscountAmount(), d1.getDiscountAmount()))
				.toList();
	}

}
