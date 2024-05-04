package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Order Status
 */
public enum OrderStatusEnum{
    PLACED("placed"),

    APPROVED("approved"),

    DELIVERED("delivered");

    private final String value;

    OrderStatusEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static OrderStatusEnum fromValue(String text) {
        for (OrderStatusEnum b : OrderStatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
