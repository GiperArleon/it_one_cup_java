package ru.vk.competition.minbenchmark.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Query not found")
public class QueryNotFoundException extends RuntimeException {

    public QueryNotFoundException(String message) {
        super(message);
        log.error("Ex: {} ", message);
    }
}
