package com.sekulicd.entity.events;

import javax.json.JsonObject;

import com.sekulicd.entity.model.MovieType;

public class BonusClubEvent extends MovieEvent {
	
	private String customerName;
	private MovieType movieType;
	
	public BonusClubEvent(String customerName, MovieType movieType) {
		super();
		this.customerName = customerName;
		this.movieType = movieType;
	}
	
	public BonusClubEvent(JsonObject jsonObject) {
		this(jsonObject.getString("customerName"), MovieType.valueOf(jsonObject.getString("movieType")));
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public MovieType getMovieType() {
		return movieType;
	}

	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}
	
}
