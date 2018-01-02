package com.sekulicd.control.query;

import javax.inject.Inject;

import com.sekulicd.control.datastore.PricingEngineDB;
import com.sekulicd.entity.model.OrderPrice;


public class PricingEngineQueryService {
	
	@Inject
	PricingEngineDB pricingEngineDB;

	public String getRentPriceForOrderId(String orderId){
		return Integer.toString(pricingEngineDB.getOrderRentPrice(orderId));
	}
	
	public String getReturnPriceForOrderId(String orderId){
		return Integer.toString(pricingEngineDB.getOrderReturnPrice(orderId));
	}
	
	public OrderPrice getOrder(String orderId){
		return pricingEngineDB.getOrder(orderId);
	}

}
