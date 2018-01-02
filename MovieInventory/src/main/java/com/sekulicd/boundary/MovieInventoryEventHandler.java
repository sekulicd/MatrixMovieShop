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

import com.sekulicd.control.command.MovieInventoryCommandService;
import com.sekulicd.entity.events.CloseOrder;
import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.entity.events.MovieRentRequetPlaced;
import com.sekulicd.kafka.boundary.EventConsumer;

import java.util.Properties;
import java.util.logging.Logger;

/**
 * Consumes events outside of inventory module
 */
@Singleton
@Startup
public class MovieInventoryEventHandler {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<MovieEvent> events;

    @Inject
    MovieInventoryCommandService movieInventoryService;

    @Inject
    Logger logger;

    public void handle(@Observes MovieRentRequetPlaced event) {
    	movieInventoryService.checkIsMovieInInventory(event.getOrderInfo());
    }
    
    public void handle(@Observes MovieRentReqAccepted event) {
    	movieInventoryService.takeFromInventory(event.getOrderInfo().getMovie());
    }
    
    public void handle(@Observes CloseOrder event) {
    	movieInventoryService.returnToInventory(event.getTitle());
    }

    @PostConstruct
    private void initConsumer() {
        kafkaProperties.put("group.id", "inventory-handler");
        String movieOrders = kafkaProperties.getProperty("movieorders.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, movieOrders);

        mes.execute(eventConsumer);
    }

    @PreDestroy
    public void closeConsumer() {
        eventConsumer.stop();
    }

}
