package com.sekulicd.entity.model;

public class OrderPrice {
	
	private String orederId;
	private int rentPrice;
	private int returnPrice;
	private PriceState priceState;
	
	public OrderPrice(String orederId) {
		super();
		this.orederId = orederId;
		this.priceState = PriceState.NOT_CALCULATED;
	}
	
	public OrderPrice(String orederId, int rentPrice, PriceState priceState) {
		super();
		this.orederId = orederId;
		this.priceState = priceState;
		this.rentPrice = rentPrice;
	}

	public String getOrederId() {
		return orederId;
	}

	public void setOrederId(String orederId) {
		this.orederId = orederId;
	}

	public int getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(int rentPrice) {
		this.rentPrice = rentPrice;
	}

	public int getReturnPrice() {
		return returnPrice;
	}

	public void setReturnPrice(int returnPrice) {
		this.returnPrice = returnPrice;
	}

	public PriceState getPriceState() {
		return priceState;
	}

	public void setPriceState(PriceState priceState) {
		this.priceState = priceState;
	}
		
}
