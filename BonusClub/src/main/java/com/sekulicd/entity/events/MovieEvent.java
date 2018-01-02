package com.sekulicd.entity.events;

import java.time.Instant;
import java.util.Objects;

import javax.json.bind.annotation.JsonbProperty;

public class MovieEvent {

	@JsonbProperty
	private final Instant instant;

	protected MovieEvent() {
		instant = Instant.now();
	}

	protected MovieEvent(final Instant instant) {
		Objects.requireNonNull(instant);
		this.instant = instant;
	}

	public Instant getInstant() {
		return instant;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		final MovieEvent that = (MovieEvent) o;

		return instant.equals(that.instant);
	}

	@Override
	public int hashCode() {
		return instant.hashCode();
	}

}
