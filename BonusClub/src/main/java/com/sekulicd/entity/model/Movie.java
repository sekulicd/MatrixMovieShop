package com.sekulicd.entity.model;

import javax.json.bind.spi.JsonbProvider;

public class Movie {

	private String title;
	private MovieType movieType;
	boolean movieInInventory = false;
	
	public Movie() {}
	
	public Movie(String title) {
		this.title = title;
	}

	public Movie(String title, MovieType movieType) {
		this.title = title;
		this.movieType = movieType;
		this.movieInInventory = true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public MovieType getMovieType() {
		return movieType;
	}

	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}
	
	public boolean isMovieInInventory() {
		return movieInInventory;
	}

	public void setMovieInInventory(boolean movieInInventory) {
		this.movieInInventory = movieInInventory;
	}

	public String toJson() {
		return JsonbProvider.provider().create().build().toJson(this);
	}  

}
