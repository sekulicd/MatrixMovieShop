package com.sekulicd.entity.model;

import java.time.LocalDate;
import java.util.UUID;

public class MovieOrderInfo {

	private final String orderId;
	private String customerName;
	private int rentPeriod;
	private LocalDate rentDate;
	private LocalDate returnDate;
	private Movie movie;
	private MovieOrderState movieOrderState;

	public MovieOrderInfo(String orderId, String customerName, int rentPeriod, LocalDate rentDate, Movie movie,
			MovieOrderState movieOrderState) {
		super();
		this.orderId = orderId;
		this.customerName = customerName;
		this.rentPeriod = rentPeriod;
		this.rentDate = rentDate;
		this.movie = movie;
		this.movieOrderState = movieOrderState;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getRentPeriod() {
		return rentPeriod;
	}

	public void setRentPeriod(int rentPeriod) {
		this.rentPeriod = rentPeriod;
	}

	public LocalDate getRentDate() {
		return rentDate;
	}

	public void setRentDate(LocalDate rentDate) {
		this.rentDate = rentDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public MovieOrderState getMovieOrderStatus() {
		return movieOrderState;
	}

	public void setMovieOrderStatus(MovieOrderState movieOrderStatus) {
		this.movieOrderState = movieOrderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public void place() {
		movieOrderState = MovieOrderState.RENT_REQ_PLACED;
	}

	public void accept() {
		movieOrderState = MovieOrderState.RENT_REQ_ACCEPTED;
	}

	public void cancel() {
		movieOrderState = MovieOrderState.RENT_REQ_CANCELLED;
	}

	public void rented() {
		movieOrderState = MovieOrderState.MOVIE_RENTED;
	}
	
	public void retrunReqPlaces() {
		movieOrderState = MovieOrderState.RETURN_REQ_PLACED;
	}
	
	public void close() {
		movieOrderState = MovieOrderState.CLOSED;
	}

}
