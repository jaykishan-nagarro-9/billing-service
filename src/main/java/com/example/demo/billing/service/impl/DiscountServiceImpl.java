package com.example.demo.billing.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.example.demo.billing.dto.AvailableDiscount;
import com.example.demo.billing.service.DiscountService;
import com.example.demo.billing.service.OpenDiscountService;
import com.example.demo.pojo.Cart;

@Service
public class DiscountServiceImpl implements DiscountService {

	@Autowired
	private OpenDiscountService openDiscountService;
	
	@Override
	public double calculateDiscount(Cart cart) {
		
		List<AvailableDiscount> calculatedDiscount = calculatePercentageDiscount(cart);
		List<AvailableDiscount> calculatedAdditionalDiscount = calculateAdditionalDiscount(cart);
		
		
		double finalDiscount = 0;
		finalDiscount += !CollectionUtils.isEmpty(calculatedDiscount) ? calculatedDiscount.get(0).getDiscountAmount() : 0;
		finalDiscount += !CollectionUtils.isEmpty(calculatedAdditionalDiscount) ? calculatedAdditionalDiscount.get(0).getDiscountAmount() : 0;
		
		prettyPrintDiscountDetail(calculatedDiscount, calculatedAdditionalDiscount, finalDiscount);
		
		return finalDiscount; 
		
	}
	
	private List<AvailableDiscount> calculatePercentageDiscount(Cart cart) {
		
		return openDiscountService.getPercentageDiscount().parallelStream()
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

	@Override
	public List<AvailableDiscount> calculateAdditionalDiscount(Cart cart) {
		
		return openDiscountService.getAdditionalDiscount().parallelStream()
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
	
	
	/**
	 * This method is to display available discount properly in console.
	 * @param calculatedDiscount
	 * @param calculatedAdditionalDiscount 
	 * @param finalDiscount2 
	 */
	private void prettyPrintDiscountDetail(List<AvailableDiscount> calculatedDiscount, List<AvailableDiscount> calculatedAdditionalDiscount, double finalDiscount) {
		
		for (AvailableDiscount availableDiscount : calculatedDiscount) {
			System.out.println(String.format("Discount %s (Reason: %s)", availableDiscount.getDiscountAmount(), availableDiscount.getDiscountDetail()));
		}
		
		for (AvailableDiscount availableDiscount : calculatedAdditionalDiscount) {
			System.out.println(String.format("Additional Discount: %s (Reason: %s)", availableDiscount.getDiscountAmount(), availableDiscount.getDiscountDetail()));
		}
		
		System.out.println("Maximum discount: " + finalDiscount);
		
		System.out.println("=============================================");
		
	}

}
