package com.sekulicd.boundary;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.kafka.boundary.EventConsumer;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

/*
 * Consumes events published from ordering module
 */
@Startup
@Singleton
public class MovieOrderUpdateConsumer {

    private EventConsumer eventConsumer;

    @Resource
    ManagedExecutorService mes;

    @Inject
    Properties kafkaProperties;

    @Inject
    Event<MovieEvent> events;

    @Inject
    Logger logger;

    @PostConstruct
    private void init() {
        kafkaProperties.put("group.id", "movie-order-consumer-" + UUID.randomUUID());
        String orders = kafkaProperties.getProperty("movieorders.topic");

        eventConsumer = new EventConsumer(kafkaProperties, ev -> {
            logger.info("firing = " + ev);
            events.fire(ev);
        }, orders);

        mes.execute(eventConsumer);
    }

    @PreDestroy
    public void close() {
        eventConsumer.stop();
    }

}
