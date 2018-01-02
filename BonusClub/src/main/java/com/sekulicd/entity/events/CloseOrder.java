package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class CloseOrder extends MovieEvent {
	
	private String orderId;
	private String title;

	public CloseOrder(String orderId, String title) {
		this.orderId = orderId;
		this.title = title;
	}

	public CloseOrder(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"), jsonObject.getString("title"));
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
