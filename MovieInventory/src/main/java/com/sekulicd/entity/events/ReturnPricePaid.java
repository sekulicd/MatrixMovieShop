package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class ReturnPricePaid extends MovieEvent {
	private String orderId;

	public ReturnPricePaid(String orderId) {
		super();
		this.setOrderId(orderId);
	}
	
	public ReturnPricePaid(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"));
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
