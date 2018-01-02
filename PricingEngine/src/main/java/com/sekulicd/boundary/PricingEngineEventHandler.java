package com.sekulicd.boundary;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.sekulicd.control.command.PricingEngineCommandService;
import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.entity.events.MovieReturnRequestPlaced;
import com.sekulicd.kafka.boundary.EventConsumer;

/**
 * Consumes events outside of pricingengine module
 */
@Singleton
@Startup
public class PricingEngineEventHandler {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<MovieEvent> events;

    @Inject
    PricingEngineCommandService pricingEngineCommandService;

    @Inject
    Logger logger;

    public void handle(@Observes MovieRentReqAccepted event) {
    	pricingEngineCommandService.calculateRentPrice(event.getOrderInfo());
    }
    
    public void handle(@Observes MovieReturnRequestPlaced event) {
    	pricingEngineCommandService.calculateRetrunPrice(event.getOrderId(), event.getMovieType(), event.getNumOfDaysExceeded());
    }


    @PostConstruct
    private void initConsumer() {
        kafkaProperties.put("group.id", "order-price-handler");
        String movieInventory = kafkaProperties.getProperty("movieorders.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, movieInventory);

        mes.execute(eventConsumer);
    }

    @PreDestroy
    public void closeConsumer() {
        eventConsumer.stop();
    }

}
