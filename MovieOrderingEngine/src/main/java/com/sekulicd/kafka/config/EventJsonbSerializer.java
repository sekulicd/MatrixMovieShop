package com.sekulicd.kafka.config;

import com.sekulicd.entity.events.MovieEvent;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;

public class EventJsonbSerializer implements JsonbSerializer<MovieEvent> {

    @Override
    public void serialize(final MovieEvent event, final JsonGenerator generator, final SerializationContext ctx) {
        generator.write("class", event.getClass().getCanonicalName());
        generator.writeStartObject("data");
        ctx.serialize("data", event, generator);
        generator.writeEnd();
    }

}
