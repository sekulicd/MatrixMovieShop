package com.sekulicd.entity.events;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.json.JsonObject;

import com.sekulicd.entity.model.Movie;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.MovieOrderState;
import com.sekulicd.entity.model.MovieType;

public class MovieRentReqAccepted extends MovieEvent {

	MovieOrderInfo orderInfo;

	public MovieRentReqAccepted(MovieOrderInfo orderInfo) {
		super();
		this.orderInfo = orderInfo;
	}
	
	public MovieRentReqAccepted(JsonObject jsonObject) {
  		this(new MovieOrderInfo(jsonObject.getJsonObject("orderInfo").getString("orderId"),
				jsonObject.getJsonObject("orderInfo").getString("customerName"),
				jsonObject.getJsonObject("orderInfo").getInt("rentPeriod"),
				LocalDate.parse(jsonObject.getJsonObject("orderInfo").getString("rentDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				new Movie(jsonObject.getJsonObject("orderInfo").getJsonObject("movie").getString("title"), MovieType.valueOf(jsonObject.getJsonObject("orderInfo").getJsonObject("movie").getString("movieType"))),
				MovieOrderState.valueOf(jsonObject.getJsonObject("orderInfo").getString("movieOrderStatus"))));
	}

	public MovieOrderInfo getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(MovieOrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
}
