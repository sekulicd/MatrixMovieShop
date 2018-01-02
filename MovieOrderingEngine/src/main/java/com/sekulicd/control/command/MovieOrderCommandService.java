package com.sekulicd.control.command;

import javax.inject.Inject;

import com.sekulicd.control.datastore.MovieOrdersDB;
import com.sekulicd.entity.events.CloseOrder;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.entity.events.MovieRentReqCancelled;
import com.sekulicd.entity.events.MovieRentRequetPlaced;
import com.sekulicd.entity.events.MovieRented;
import com.sekulicd.entity.events.MovieReturnRequestPlaced;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.MovieType;
import com.sekulicd.kafka.boundary.EventProducer;

public class MovieOrderCommandService {

    @Inject
    EventProducer eventProducer;

    @Inject
    MovieOrdersDB movieOrders;

    public void placeOrder(MovieOrderInfo orderInfo) {
        eventProducer.publish(new MovieRentRequetPlaced(orderInfo));
    }
    
    public void acceptOrder(String orderId, MovieType movieType) {
    	movieOrders.getOrder(orderId).getMovie().setMovieType(movieType);
    	final MovieOrderInfo orderInfo = movieOrders.getOrder(orderId);
        eventProducer.publish(new MovieRentReqAccepted(orderInfo));
    }

    public void cancelOrder(String orderId) {
        eventProducer.publish(new MovieRentReqCancelled(orderId));
    }
    
    public void movieRented(String orderId){
    	eventProducer.publish(new MovieRented(orderId));
    }
    
    public void placeReturnRequest(String orderId, MovieType movieType, int numOfDaysExceeded){
    	eventProducer.publish(new MovieReturnRequestPlaced(orderId, movieType, numOfDaysExceeded));
    }
    
    public void closeOrder(String orderId){
    	eventProducer.publish(new CloseOrder(orderId, movieOrders.getOrder(orderId).getMovie().getTitle()));
    }

}
