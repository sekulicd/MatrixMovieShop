package com.sekulicd.entity.events;

import javax.json.JsonObject;

import com.sekulicd.entity.model.Movie;
import com.sekulicd.entity.model.MovieType;
import javax.json.bind.annotation.JsonbProperty;

public class MovieAddToInventory extends MovieEvent {
	
    @JsonbProperty
	private Movie movie;

	public MovieAddToInventory(Movie movie) {
		super();
		this.movie = movie;
	}
	
	 public MovieAddToInventory(JsonObject jsonObject) {
        this(new Movie(jsonObject.getJsonObject("movie").getString("title"), MovieType.valueOf(jsonObject.getJsonObject("movie").getString("movieType"))));
    }

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
}
