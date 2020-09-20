package ru.geekbrains.spring.ishop.rest.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.ishop.exception.ErrorResponse;
import ru.geekbrains.spring.ishop.exception.NotFoundException;
import ru.geekbrains.spring.ishop.exception.OutEntityDeserializeException;
import ru.geekbrains.spring.ishop.exception.OutEntitySerializeException;

@RestController
public abstract class AbstractResource {

    /**
     * Метод перехватывает ошибку 500 и возвращает 404
     * @param exception - NotFoundException
     * @return - NOT_FOUND(404, "The Product with id=99 is not found!")
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(NotFoundException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Метод перехватывает ошибку 500 и возвращает 400 - Bad request
     * @param exception - IllegalArgumentException
     * @return - BAD_REQUEST(400, "Illegal Argument. Look that you write! For input string: \"wrong_argument\""),
     */
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> illegalArgumentExceptionHandler(IllegalArgumentException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Illegal Argument. Look that you write! " + exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> outEntityDeserializeExceptionHandler(OutEntityDeserializeException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> outEntitySerializeExceptionHandler(OutEntitySerializeException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(exception.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
