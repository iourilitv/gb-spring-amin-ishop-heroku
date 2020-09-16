package ru.geekbrains.spring.ishop.rest.deserializers.interfaces;

import ru.geekbrains.spring.ishop.rest.outentities.OutEntity;

public interface IEntityBuilder<E> {
    E create(OutEntity outEntity);
}
