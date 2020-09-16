package ru.geekbrains.spring.ishop.rest.deserializers;

import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

public interface IEntityBuilder<E> {
//    E create(Map<String,Object> fields);
    E create(OutEntity outEntity);
}
