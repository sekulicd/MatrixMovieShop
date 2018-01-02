package com.sekulicd.control.query;

import javax.inject.Inject;

import com.sekulicd.control.datastore.BonusClubDB;
import com.sekulicd.entity.model.BonusClub;

public class BonusClubQueryService {

	@Inject
	BonusClubDB bonusClub;
	
	public String getCustomerBonus(String customerName){
		return  Integer.toString(bonusClub.getCustomerBonusInfo(customerName).getBonus());
	}
	
	public BonusClub getCustomer(String customerName){
		return  bonusClub.getCustomerBonusInfo(customerName);
	}
	
}
