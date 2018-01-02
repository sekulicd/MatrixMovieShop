package com.sekulicd.entity.events;

import javax.json.JsonObject;

import com.sekulicd.entity.model.MovieType;

public class MovieReturnRequestPlaced extends MovieEvent {
	
	private String orderId;
	private MovieType movieType;
	private int numOfDaysExceeded;
	
	
	public MovieReturnRequestPlaced(String orderId, MovieType movieType, int numOfDaysExceeded) {
		super();
		this.orderId = orderId;
		this.movieType = movieType;
		this.numOfDaysExceeded = numOfDaysExceeded;
	}
	
	public MovieReturnRequestPlaced(JsonObject jsonObject) {
		this(jsonObject.getString("orderId"), MovieType.valueOf(jsonObject.getString("movieType")), jsonObject.getInt("numOfDaysExceeded"));
	}

	public MovieType getMovieType() {
		return movieType;
	}

	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}

	public int getNumOfDaysExceeded() {
		return numOfDaysExceeded;
	}

	public void setNumOfDaysExceeded(int numOfDaysExceeded) {
		this.numOfDaysExceeded = numOfDaysExceeded;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
}
