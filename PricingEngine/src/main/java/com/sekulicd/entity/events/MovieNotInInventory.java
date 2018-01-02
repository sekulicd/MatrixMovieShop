package com.sekulicd.entity.events;

import java.time.Instant;
import java.util.UUID;

import javax.json.JsonObject;

public class MovieNotInInventory extends MovieEvent {
	
	private final UUID orderId;

    public MovieNotInInventory(final UUID orderId) {
        this.orderId = orderId;
    }

    public MovieNotInInventory(final UUID orderId, final Instant instant) {
        super(instant);
        this.orderId = orderId;
    }

    public MovieNotInInventory(JsonObject jsonObject) {
        this(UUID.fromString(jsonObject.getString("orderId")), Instant.parse(jsonObject.getString("instant")));
    }

    public UUID getOrderId() {
        return orderId;
    }
}
