package com.sekulicd.boundary;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.sekulicd.control.command.MovieOrderCommandService;
import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.entity.events.MovieInInventory;
import com.sekulicd.entity.events.MovieNotInInventory;
import com.sekulicd.entity.events.RentPricePaid;
import com.sekulicd.entity.events.ReturnPricePaid;
import com.sekulicd.kafka.boundary.EventConsumer;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Consumes events outside of ordering module
 */
@Singleton
@Startup
public class MovieOrderEventHandler {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<MovieEvent> events;

    @Inject
    MovieOrderCommandService movieOrderCommandService;

    @Inject
    Logger logger;

    public void handle(@Observes MovieInInventory event) {
    	movieOrderCommandService.acceptOrder(event.getOrderId().toString(), event.getMovieType());
    }

    public void handle(@Observes MovieNotInInventory event) {
    	movieOrderCommandService.cancelOrder(event.getOrderId().toString());
    }
    
    public void handle(@Observes RentPricePaid event) {
    	movieOrderCommandService.movieRented(event.getOrderId());
    }
    
    public void handle(@Observes ReturnPricePaid event) {
    	movieOrderCommandService.closeOrder(event.getOrderId());
    }

    @PostConstruct
    private void initConsumer() {
        kafkaProperties.put("group.id", "movie-order-handler");
        String movieInventory = kafkaProperties.getProperty("movieinventory.topic");
        String moviePricingEngine = kafkaProperties.getProperty("moviepricingengine.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, movieInventory, moviePricingEngine);

        mes.execute(eventConsumer);
    }

    @PreDestroy
    public void closeConsumer() {
        eventConsumer.stop();
    }

}
