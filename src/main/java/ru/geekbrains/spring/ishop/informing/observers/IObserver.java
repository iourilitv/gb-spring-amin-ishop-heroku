package ru.geekbrains.spring.ishop.informing.observers;

import ru.geekbrains.spring.ishop.informing.subjects.AbstractSubject;

public interface IObserver {
    void update(AbstractSubject subject, Object arg);
}
