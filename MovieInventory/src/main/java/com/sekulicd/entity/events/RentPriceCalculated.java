package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class RentPriceCalculated extends MovieEvent {
	
	private String orderId;
	private int rentPrice;
	
	public RentPriceCalculated(String orderId, int rentPrice) {
		super();
		this.orderId = orderId;
		this.rentPrice = rentPrice;
	}
	
	public RentPriceCalculated(JsonObject jsonObject) {
        this(jsonObject.getString("orderId"),jsonObject.getInt("rentPrice"));
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}
	
}
