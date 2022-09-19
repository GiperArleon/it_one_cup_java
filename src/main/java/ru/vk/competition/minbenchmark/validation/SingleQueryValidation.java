package ru.vk.competition.minbenchmark.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vk.competition.minbenchmark.entity.SingleQuery;

@Slf4j
@Component
@RequiredArgsConstructor
public class SingleQueryValidation extends RootValidation {

    public boolean isValid(SingleQuery singleQuery) {

        if(singleQuery.getQueryId() == null) {
            log.error("QueryId is null");
            return false;
        }

        if(singleQuery.getQuery() == null) {
            log.error("Query is null");
            return false;
        }

        if(singleQuery.getQuery().length() > MAX_SQL_QUERY_SIZE) {
            log.error("wrong sql request {}, length exceed", singleQuery.getQuery());
            return false;
        }
        return true;
    }
}
