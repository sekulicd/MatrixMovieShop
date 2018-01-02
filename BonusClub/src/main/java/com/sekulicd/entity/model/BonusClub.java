package com.sekulicd.entity.model;

public class BonusClub {
	
	private String customerName;
	private int bonus;
	
	public BonusClub(String customerName, int bonus) {
		super();
		this.customerName = customerName;
		this.bonus = bonus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
}
