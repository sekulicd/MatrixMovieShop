package com.sekulicd.kafka.config;

import com.sekulicd.entity.events.MovieEvent;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.logging.Logger;

public class EventDeserializer implements Deserializer<MovieEvent> {

    private static final Logger logger = Logger.getLogger(EventDeserializer.class.getName());

    @Override
    public void configure(final Map<String, ?> configs, final boolean isKey) {
        // nothing to configure
    }

    @Override
    public MovieEvent deserialize(final String topic, final byte[] data) {
        try (ByteArrayInputStream input = new ByteArrayInputStream(data)) {
            final JsonObject jsonObject = Json.createReader(input).readObject();
            final Class<? extends MovieEvent> eventClass = (Class<? extends MovieEvent>) Class.forName(jsonObject.getString("class"));
            return eventClass.getConstructor(JsonObject.class).newInstance(jsonObject.getJsonObject("data"));
        } catch (Exception e) {
            logger.severe("Could not deserialize event: " + e.getMessage());
            throw new SerializationException("Could not deserialize event", e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}
