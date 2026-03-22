package com.lexho.user_service.enums;

import java.util.Set;

public enum OrderStatus {

    CREATED,
    CONFIRMED,
    PREPARING,
    OUT_FOR_DELIVERY,
    DELIVERED,
    CANCELLED;

    public Set<OrderStatus> getAllowedTransitions() {
        return switch (this) {
            case CREATED -> Set.of(CONFIRMED, CANCELLED);
            case CONFIRMED -> Set.of(PREPARING, CANCELLED);
            case PREPARING -> Set.of(OUT_FOR_DELIVERY);
            case OUT_FOR_DELIVERY -> Set.of(DELIVERED);
            case DELIVERED, CANCELLED -> Set.of();
        };
    }

    public boolean canTransitionTo(OrderStatus next) {
        return getAllowedTransitions().contains(next);
    }
    public boolean canCancel() {
        return this == CREATED || this == CONFIRMED;
    }
}