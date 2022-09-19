package ru.vk.competition.minbenchmark.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Query for update not found")
public class UpdateQueryNotFoundException extends RuntimeException {
    public UpdateQueryNotFoundException(String message) {
        super(message);
        log.error("Ex: {} ", message);
    }
}
