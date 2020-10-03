package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Builder;
import lombok.Data;
import ru.geekbrains.spring.ishop.entity.ActionType;
import ru.geekbrains.spring.ishop.entity.Event;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class OutEntity {
    public enum Fields {store, entityType, entityFields}

    private final String store = "gb-spring-amin-ishop-heroku";

    private String entityType;

    private Map<String, Object> entityFields;

    @Override
    public String toString() {
        return "OutEntity{" +
                "store='" + store + '\'' +
                ", entityType='" + entityType + '\'' +
                ", entityFields=" + entityFields +
                '}';
    }

    public String toJsonString() {
        return "{" +
                "store=" + store +
                ", entityType=" + entityType +
                ", entityFields=" + entityFields +
                '}';
    }
}
