package com.sekulicd.entity.events;

import javax.json.JsonObject;

//update json constructor
public class MovieRentReqCancelled extends MovieEvent {
	
	private String orderId;

	public MovieRentReqCancelled(String orderId) {
		super();
		this.orderId = orderId;
	}
	
	public MovieRentReqCancelled(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"));
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
}
