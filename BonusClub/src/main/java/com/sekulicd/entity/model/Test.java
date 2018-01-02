package com.sekulicd.entity.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Test {

static Map<String, BonusClub> bonusClub = new ConcurrentHashMap<>();
	
	public static void main(String[] arg){
		BonusClub c1 = new BonusClub("dusan", 5);
		bonusClub.put("dusan", c1);
		
		BonusClub c2 = new BonusClub("marko",3);
		Test.insertOrReplace(c2.getCustomerName(), c2);
		Test.insertOrReplace(c1.getCustomerName(), c2);
		System.out.println(bonusClub.get("dusan").getBonus());
		System.out.println(bonusClub.get("marko").getBonus());
	}
	
	public static void insertOrReplace(String customerName, BonusClub newBonusClubValue){
		bonusClub.merge(customerName, newBonusClubValue, 
								(oldValue, newValue)-> 
								{
									oldValue.setBonus(oldValue.getBonus() + newValue.getBonus());
									return oldValue;
								}
							);

	}
}
