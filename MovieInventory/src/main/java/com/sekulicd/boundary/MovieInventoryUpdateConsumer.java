package com.sekulicd.boundary;

import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import com.sekulicd.entity.events.MovieEvent;
import com.sekulicd.kafka.boundary.EventConsumer;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Consumes events outside of ordering module
 */
@Singleton
@Startup
public class MovieInventoryUpdateConsumer {
	
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
		kafkaProperties.put("group.id", "movie-inventory-consumer-" + UUID.randomUUID());
		String movieInventory = kafkaProperties.getProperty("movieinventory.topic");

		eventConsumer = new EventConsumer(kafkaProperties, ev -> {
			logger.info("firing = " + ev);
			events.fire(ev);
		}, movieInventory);

		mes.execute(eventConsumer);
	}

	@PreDestroy
	public void close() {
		eventConsumer.stop();
	}
}
