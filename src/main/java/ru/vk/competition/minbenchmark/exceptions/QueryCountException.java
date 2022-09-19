package ru.vk.competition.minbenchmark.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Exception in jdbc count query")
public class QueryCountException extends RuntimeException {

    public QueryCountException(String message) {
        super(message);
        log.error("Ex: {} ", message);
    }
}
