package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Builder
@Data
public class OutEntity {

    private final String store = "gb-spring-amin-ishop-heroku";

    private String entityType;

    private Map<String, Object> entityFields;

}
