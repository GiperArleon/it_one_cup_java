package ru.vk.competition.minbenchmark.service;

import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.dto.table.Table;

public interface TableService {
    ResponseEntity<Void> createTable(Table table);
    ResponseEntity<Table> getTableByName(String name);
    ResponseEntity<Void> dropTableByName(String name);
    ResponseEntity<Void> addNewQueryResult(QueryResult queryResult);
}
