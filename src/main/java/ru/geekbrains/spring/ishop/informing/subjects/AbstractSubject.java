package ru.geekbrains.spring.ishop.informing.subjects;

import ru.geekbrains.spring.ishop.informing.observers.IObserver;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubject {
    private final List<IObserver> observers = new ArrayList<>();

    public void attach(IObserver observer) {
        if(observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    protected void notice(Object arg) {
        for (IObserver o: observers) {
            o.update(this, arg);
        }
    }

}
