package com.sekulicd.kafka.boundary;

import com.sekulicd.entity.events.MovieEvent;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.ProducerFencedException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class EventProducer {

    private Producer<String, MovieEvent> producer;
    private String topic;

    @Inject
    Properties kafkaProperties;

    @Inject
    Logger logger;

    @PostConstruct
    private void init() {
        kafkaProperties.put("transactional.id", UUID.randomUUID().toString());
        producer = new KafkaProducer<>(kafkaProperties);
        topic = kafkaProperties.getProperty("moviebonusclub.topic");
        producer.initTransactions();
    }

    public void publish(MovieEvent event) {
        final ProducerRecord<String, MovieEvent> record = new ProducerRecord<>(topic, event);
        try {
            producer.beginTransaction();
            logger.info("publishing = " + record);
            producer.send(record);
            producer.commitTransaction();
        } catch (ProducerFencedException e) {
        	e.printStackTrace();
            producer.close();
        } catch (KafkaException e) {
        	e.printStackTrace();
            producer.abortTransaction();
        }
    }

    @PreDestroy
    public void close() {
        producer.close();
    }

}

