package com.sekulicd.entity.events;

import java.time.Instant;
import java.util.UUID;

import javax.json.JsonObject;

public class MovieNotInInventory extends MovieEvent {
	
	private final String orderId;

    public MovieNotInInventory(final String orderId) {
        this.orderId = orderId;
    }

    public MovieNotInInventory(final String orderId, final Instant instant) {
        super(instant);
        this.orderId = orderId;
    }

    public MovieNotInInventory(JsonObject jsonObject) {
        this(jsonObject.getString("orderId"), Instant.parse(jsonObject.getString("instant")));
    }

    public String getOrderId() {
        return orderId;
    }
}
