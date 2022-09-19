package ru.vk.competition.minbenchmark.service;

import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import java.util.List;

public interface TableQueryService {
    ResponseEntity<Void> addQueryWithQueryId(TableQuery tableQuery);
    ResponseEntity<Void> updateQueryWithQueryId(TableQuery tableQuery);
    ResponseEntity<Void> deleteQueryById(Integer id);
    ResponseEntity<Void> executeTableQuery(Integer id);
    ResponseEntity<TableQuery> getQueryById(Integer id);
    List<TableQuery> getAllQueries();
    List<TableQuery> getAllQueriesByName(String name);
    ResponseEntity<Void> addNewQueryResult(QueryResult queryResult);
}
