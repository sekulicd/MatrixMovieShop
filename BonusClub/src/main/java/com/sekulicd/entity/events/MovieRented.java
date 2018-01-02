package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class MovieRented extends MovieEvent {

	private String orderId;

	public MovieRented(String orderId) {
		this.orderId = orderId;
	}

	public MovieRented(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"));
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

}
