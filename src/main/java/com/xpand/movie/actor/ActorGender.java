package com.xpand.movie.actor;

import java.util.Arrays;
import java.util.Optional;

public enum ActorGender {
    FEMALE("Female"),
    MALE("Male");

    private final String type;

    ActorGender(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static Optional<ActorGender> fromType(String type){
        return Arrays.stream(values())
                .filter(item -> item.getType().equalsIgnoreCase(type))
                .findFirst();
    }
}
