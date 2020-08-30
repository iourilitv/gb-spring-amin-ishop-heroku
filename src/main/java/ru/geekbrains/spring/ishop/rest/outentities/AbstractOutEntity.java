package ru.geekbrains.spring.ishop.rest.outentities;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

//@PropertySource("classpath:application.properties") //did not help
@Data
public abstract class AbstractOutEntity {

    //TODO IDEA видит, но при работе получаем null
//    @Value("${spring.application.name}")
//    private String store;
    private String store = "gb-spring-amin-ishop-heroku";

    private String entity = this.getClass().getSimpleName();

}
