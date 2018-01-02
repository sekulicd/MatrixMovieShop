package com.sekulicd.control.query;

import javax.inject.Inject;

import com.sekulicd.control.datastore.MovieOrdersDB;
import com.sekulicd.entity.model.MovieOrderInfo;

public class MovieOrderQueryService {

	@Inject
	MovieOrdersDB movieOrders;

	public MovieOrderInfo getOrder(String orderId) {
		return movieOrders.getOrder(orderId);
	}

}
