package com.sekulicd.kafka.boundary;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import com.sekulicd.entity.events.MovieEvent;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javax.inject.Inject;

import static java.util.Arrays.asList;
import java.util.logging.Level;

public class EventConsumer implements Runnable {

    private final KafkaConsumer<String, MovieEvent> consumer;
    private final Consumer<MovieEvent> eventConsumer;
    private final AtomicBoolean closed = new AtomicBoolean();
    
    @Inject
	Logger logger;

    public EventConsumer(Properties kafkaProperties, Consumer<MovieEvent> eventConsumer, String... topics) {
        this.eventConsumer = eventConsumer;
        consumer = new KafkaConsumer<>(kafkaProperties);
        consumer.subscribe(asList(topics));
    }

    @Override
    public void run() {
        try {
            while (!closed.get()) {
                consume();
            }
        } catch (WakeupException e) {
            // will wakeup for closing
        } finally {
            consumer.close();
        }
    }

    private void consume() {
        ConsumerRecords<String, MovieEvent> records = consumer.poll(1000);
        for (ConsumerRecord<String, MovieEvent> record : records) {
        	//logger.info("consuming = " + record);
            eventConsumer.accept(record.value());
        }
        consumer.commitSync();
    }

    public void stop() {
        closed.set(true);
        consumer.wakeup();
    }

}
