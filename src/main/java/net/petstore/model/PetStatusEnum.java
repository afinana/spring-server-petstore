package net.petstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * pet status in the store
 */
public enum PetStatusEnum {
    AVAILABLE("available"),

    PENDING("pending"),

    SOLD("sold");

    private final String value;

    PetStatusEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static PetStatusEnum fromValue(String text) {
        for (PetStatusEnum b : PetStatusEnum.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }
}
