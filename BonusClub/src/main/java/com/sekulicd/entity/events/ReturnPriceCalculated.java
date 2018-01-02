package com.sekulicd.entity.events;

import javax.json.JsonObject;

public class ReturnPriceCalculated extends MovieEvent {
	
	private String orderId;
	private int returnPrice;
	
	public ReturnPriceCalculated(String orderId, int rentPrice) {
		super();
		this.orderId = orderId;
		this.returnPrice = rentPrice;
	}
	
	public ReturnPriceCalculated(JsonObject jsonObject) {
        this(jsonObject.getString("orderId"),jsonObject.getInt("returnPrice"));
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(int returnPrice) {
		this.returnPrice = returnPrice;
	}

}
