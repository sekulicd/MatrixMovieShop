package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class RentPricePaid extends MovieEvent {
	private String orderId;

	public RentPricePaid(String orderId) {
		super();
		this.setOrderId(orderId);
	}
	
	public RentPricePaid(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"));
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}