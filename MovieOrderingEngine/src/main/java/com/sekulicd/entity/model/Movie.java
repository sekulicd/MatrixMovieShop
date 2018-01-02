package com.sekulicd.entity.model;

import javax.json.bind.spi.JsonbProvider;

public class Movie {

	private String title;
	private MovieType movieType;
	public int price;
	
	public Movie() {
		super();
	}

	public Movie(String title) {
		super();
		this.title = title;
	}
	
	public Movie(String title, MovieType movieType) {
		super();
		this.movieType = movieType;
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

	public String toJson() {
		return JsonbProvider.provider().create().build().toJson(this);
	}

}
