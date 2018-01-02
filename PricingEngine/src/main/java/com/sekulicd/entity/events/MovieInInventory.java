package com.sekulicd.entity.events;

import java.time.Instant;
import java.util.UUID;

import javax.json.JsonObject;

public class MovieInInventory extends MovieEvent {
	private final String orderId;

    public MovieInInventory(final String orderId) {
        this.orderId = orderId;
    }

    public MovieInInventory(final String orderId, final Instant instant) {
        super(instant);
        this.orderId = orderId;
    }

    public MovieInInventory(JsonObject jsonObject) {
        this(jsonObject.getString("orderId"));
    }

    public String getOrderId() {
        return orderId;
    }
}
