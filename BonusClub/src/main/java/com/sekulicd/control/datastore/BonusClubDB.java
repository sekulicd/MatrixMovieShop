package com.sekulicd.control.datastore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

import com.sekulicd.entity.events.BonusClubEvent;
import com.sekulicd.entity.events.MovieAddToInventory;
import com.sekulicd.entity.model.BonusClub;
import com.sekulicd.entity.model.MovieType;

/*
 * In-memory data representation
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class BonusClubDB{
	
	private static final int bonusPointsForOtherMovieType = 1;
	private static final int bonusPointsForNewReleaseMovieTYpe = 2;
	private static final Map<MovieType, Integer> movieBonus;
    static {
    	
    	Map<MovieType, Integer> temp = new HashMap<MovieType, Integer>();
    	temp.put(MovieType.NEW_RELEASE, bonusPointsForNewReleaseMovieTYpe);
    	movieBonus = Collections.unmodifiableMap(temp);
    }

	private Map<String, BonusClub> bonusClub = new ConcurrentHashMap<>();
	
	public void apply(@Observes BonusClubEvent bonusClubEvent) {
		increaseCustomerBonus(bonusClubEvent.getCustomerName(), bonusClubEvent.getMovieType());
    }
	
	public boolean customerExist(String customerName){
		return bonusClub.containsKey(customerName);
	}
	
	public BonusClub getCustomerBonusInfo(String customerName){		
		return bonusClub.get(customerName);
	}
	
	public void increaseCustomerBonus(String customerName, MovieType movieType) {
		int bonus = 0;
		switch (movieType) {

		case NEW_RELEASE:
			bonus = movieBonus.get(movieType);
			break;
		default:
			bonus = bonusPointsForOtherMovieType;
			break;

		}
		BonusClub newBonusClubValue = new BonusClub(customerName, bonus);
		insertOrReplace(customerName, newBonusClubValue);
	}
	
	public void insertOrReplace(String customerName, BonusClub newBonusClubValue){
		this.bonusClub.merge(customerName, newBonusClubValue, 
								(oldValue, newValue)-> 
								{
									oldValue.setBonus(oldValue.getBonus() + newValue.getBonus());
									return oldValue;
								}
							);
	}
}
