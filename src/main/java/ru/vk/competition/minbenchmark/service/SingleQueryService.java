package ru.vk.competition.minbenchmark.service;

import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import java.util.List;

public interface SingleQueryService {
    ResponseEntity<Void> addQueryWithQueryId(SingleQuery singleQuery);
    ResponseEntity<Void> updateQueryWithQueryId(SingleQuery singleQuery);
    ResponseEntity<Void> deleteQueryById(Integer id);
    ResponseEntity<Void> executeSingleQuery(Integer id);
    ResponseEntity<SingleQuery> getQueryById(Integer id);
    List<SingleQuery> getAllQueries();
    ResponseEntity<Void> addNewQueryResult(QueryResult singleQueryResult);
}
