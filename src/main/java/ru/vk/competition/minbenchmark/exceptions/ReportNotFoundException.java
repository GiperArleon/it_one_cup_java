package ru.vk.competition.minbenchmark.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE, reason = "Report not found")
public class ReportNotFoundException extends RuntimeException {
    public ReportNotFoundException(String message) {
        super(message);
        log.error("Ex: {} ", message);
    }
}
