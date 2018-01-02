package com.sekulicd.entity.model;

import java.util.EnumSet;

public enum MovieType {
	OLD, NEW_RELEASE, REGULAR;

	public static <E extends Enum<E>> boolean enumContains(String value) {
		try {
			return EnumSet.allOf(MovieType.class).contains(Enum.valueOf(MovieType.class, value));
		} catch (Exception e) {
			return false;
		}
	}
}
