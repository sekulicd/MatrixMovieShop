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

import com.sekulicd.control.command.BonusClubCommandService;
import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.entity.events.MovieRentReqAccepted;
import com.sekulicd.kafka.boundary.EventConsumer;

/**
 * Consumes events outside of inventory module
 */
@Singleton
@Startup
public class BonusClubEventHandler {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<MovieEvent> events;

    @Inject
    BonusClubCommandService bonusClubCommandService;

    @Inject
    Logger logger;
    
    public void handle(@Observes MovieRentReqAccepted event) {
    	bonusClubCommandService.increaseCustomerBonus(event.getOrderInfo().getCustomerName(), event.getOrderInfo().getMovie().getMovieType());
    }

    @PostConstruct
    private void initConsumer() {
        kafkaProperties.put("group.id", "bonus-club-handler");
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
