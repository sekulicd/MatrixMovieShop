package com.sekulicd.control.datastore;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.event.Observes;

import com.sekulicd.entity.events.CloseOrder;
import com.sekulicd.entity.events.MovieInInventory;
import com.sekulicd.entity.events.MovieNotInInventory;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.entity.events.MovieRentReqCancelled;
import com.sekulicd.entity.events.MovieRentRequetPlaced;
import com.sekulicd.entity.events.MovieRented;
import com.sekulicd.entity.events.MovieReturnRequestPlaced;
import com.sekulicd.entity.model.MovieOrderInfo;

/*
 * In-memory data representation, observes for events published from MovieOrderingEngine
 */
@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class MovieOrdersDB {

	private Map<String, MovieOrderInfo> movieOrders = new ConcurrentHashMap<>();

	public MovieOrderInfo getOrder(final String orderId) {
		return movieOrders.get(orderId);
	}

	public void apply(@Observes MovieRentRequetPlaced event) {
		movieOrders.putIfAbsent(event.getOrderInfo().getOrderId(), event.getOrderInfo());
		applyFor(event.getOrderInfo().getOrderId(), MovieOrderInfo::place);//?check CoffeeOrders
	}

	public void apply(@Observes MovieRentReqAccepted event) {
		applyFor(event.getOrderInfo().getOrderId(), MovieOrderInfo::accept);
	}

	public void apply(@Observes MovieRentReqCancelled event) {
		applyFor(event.getOrderId(), MovieOrderInfo::cancel);
	}
	
	
	public void apply(@Observes MovieRented event) {
		applyFor(event.getOrderId(), MovieOrderInfo::rented);
	}
	
	public void apply(@Observes MovieReturnRequestPlaced event) {
		applyFor(event.getOrderId(), MovieOrderInfo::retrunReqPlaces);
	}
	
	public void apply(@Observes CloseOrder event) {
		applyFor(event.getOrderId(), MovieOrderInfo::close);
	}


	private void applyFor(final String orderId, final Consumer<MovieOrderInfo> consumer) {
		final MovieOrderInfo movieOrder = movieOrders.get(orderId);
		if (movieOrder != null)
			consumer.accept(movieOrder);
	}

}
