package com.sekulicd.entity.events;

import javax.json.JsonObject;

import com.sekulicd.entity.model.MovieType;

public class MovieInInventory extends MovieEvent {
	private final String orderId;
	private final MovieType movieType;

    public MovieInInventory(String orderId, MovieType movieType) {
        this.orderId = orderId;
        this.movieType = movieType;
    }

    public MovieInInventory(JsonObject jsonObject) {
        this(jsonObject.getString("orderId"), MovieType.valueOf(jsonObject.getString("movieType")));
    }
    
    public MovieType getMovieType() {
		return movieType;
	}

	public String getOrderId() {
        return orderId;
    }
}
