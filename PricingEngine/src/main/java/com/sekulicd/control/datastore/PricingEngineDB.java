package com.sekulicd.control.datastore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

import com.sekulicd.entity.events.MovieInInventory;
import com.sekulicd.entity.events.MovieNotInInventory;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.entity.events.MovieRentReqCancelled;
import com.sekulicd.entity.events.MovieRentRequetPlaced;
import com.sekulicd.entity.events.RentPriceCalculated;
import com.sekulicd.entity.events.RentPricePaid;
import com.sekulicd.entity.events.ReturnPriceCalculated;
import com.sekulicd.entity.events.ReturnPricePaid;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.OrderPrice;
import com.sekulicd.entity.model.PriceState;

/*
 * In-memory data representation, observes for events published from MovieOrderingEngine
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class PricingEngineDB {

	private Map<String, OrderPrice> orderPrice = new ConcurrentHashMap<>();

	public int getOrderRentPrice(String orderId) {
		return orderPrice.get(orderId).getRentPrice();
	}
	
	public int getOrderReturnPrice(String orderId) {
		return orderPrice.get(orderId).getReturnPrice();
	}
	
	public OrderPrice getOrder(String orderId) {
		return orderPrice.get(orderId);
	}

	public void apply(@Observes RentPriceCalculated event) {
		orderPrice.putIfAbsent(event.getOrderId(), new OrderPrice(event.getOrderId(), event.getRentPrice(), PriceState.RENT_PRICE_CALCULATED));
	}
	
	public void apply(@Observes RentPricePaid event) {
		orderPrice.get(event.getOrderId()).setPriceState(PriceState.RENT_PRICE_PAIED);
	}
	
	public void apply(@Observes ReturnPriceCalculated event) {
		OrderPrice tmpOrderPrice = orderPrice.get(event.getOrderId());
		tmpOrderPrice.setReturnPrice(event.getReturnPrice());
		tmpOrderPrice.setPriceState(PriceState.RETURN_PRICE_CALCULATED);
		orderPrice.put(event.getOrderId(), tmpOrderPrice);
	}
	
	public void apply(@Observes ReturnPricePaid event) {
		orderPrice.get(event.getOrderId()).setPriceState(PriceState.RETURN_PRICE_PAID);
	}

}
