package ru.vk.competition.minbenchmark.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {QueryNotFoundException.class})
    protected ResponseEntity<Object> notFound() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UpdateQueryNotFoundException.class,
                               ReportNotFoundException.class,
                               QueryCountException.class,
                               InvalidTableException.class })
    protected ResponseEntity<Object> notFoundforUpdate() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handle() {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
