package com.sekulicd.control.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.sekulicd.entity.events.RentPriceCalculated;
import com.sekulicd.entity.events.RentPricePaid;
import com.sekulicd.entity.events.ReturnPriceCalculated;
import com.sekulicd.entity.events.ReturnPricePaid;
import com.sekulicd.entity.model.MovieOrderInfo;
import com.sekulicd.entity.model.MovieType;
import com.sekulicd.kafka.boundary.EventProducer;

public class PricingEngineCommandService {
	
	private static final int basicPrice = 30;
	private static final int premiumPrice = 40;
	private static final Map<MovieType, Integer> moviePrice;
    static {
    	
    	Map<MovieType, Integer> temp = new HashMap<MovieType, Integer>();
    	temp.put(MovieType.OLD, basicPrice);
    	temp.put(MovieType.REGULAR, basicPrice);
    	temp.put(MovieType.NEW_RELEASE, premiumPrice);
		moviePrice = Collections.unmodifiableMap(temp);
    }

    @Inject
    EventProducer eventProducer;


    public void calculateRentPrice(MovieOrderInfo orderInfo) {
    	int movieRentPrice = rentPrice(orderInfo, orderInfo.getMovie().getMovieType());
        eventProducer.publish(new RentPriceCalculated(orderInfo.getOrderId(), movieRentPrice));
    }
    
    public void payRentPrice(String orderId){
    	eventProducer.publish(new RentPricePaid(orderId));
    }
    
    public void payReturntPrice(String orderId){
    	eventProducer.publish(new ReturnPricePaid(orderId));
    }
    
    public void calculateRetrunPrice(String ordrerId, MovieType movieType, int numOfDaysExceeded){
    	int movieTypePrice = moviePrice.get(movieType);
    	eventProducer.publish(new ReturnPriceCalculated(ordrerId, movieTypePrice * numOfDaysExceeded));
    }
    
    public int rentPrice(MovieOrderInfo orderInfo, MovieType movieType){
    	int movieRentPrice = 0;
    	int movieTypePrice = moviePrice.get(orderInfo.getMovie().getMovieType());
    	int rentPeriod = orderInfo.getRentPeriod();
    	switch(movieType){
    	
			case OLD: 
				if(rentPeriod <= 5){
					movieRentPrice = movieTypePrice;
				}else{
					movieRentPrice = movieTypePrice + (rentPeriod - 5) * movieTypePrice;
				}
			break;
			
			case REGULAR: 
				if(rentPeriod <= 3){
					movieRentPrice = movieTypePrice;
				}else{
					movieRentPrice = movieTypePrice + (rentPeriod - 3) * movieTypePrice;
				}
			break;
			
			case NEW_RELEASE: 
				movieRentPrice = movieTypePrice * rentPeriod;
			break;
    	}
    	return movieRentPrice;
    }

}
