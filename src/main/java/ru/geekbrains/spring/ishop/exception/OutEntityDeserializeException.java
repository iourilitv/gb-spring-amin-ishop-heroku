package ru.geekbrains.spring.ishop.exception;

import com.google.gson.JsonParseException;

public class OutEntityDeserializeException extends JsonParseException {

    public OutEntityDeserializeException(String msg) {
        super(msg);
    }
}
