package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.service.TableQueryService;
import java.util.List;

@RestController
@RequestMapping("/api/table-query")
@RequiredArgsConstructor
public class TableQueryController {
    private final TableQueryService tableQueryService;

    @PostMapping("/add-new-query-to-table-result")
    public ResponseEntity<Void> addNewQueryToTableResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @PostMapping("/add-new-query-to-table")
    public ResponseEntity<Void> addNewQueryToTable(@RequestBody TableQuery tableQuery, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.addQueryWithQueryId(tableQuery);
    }

    @PostMapping("/add-modify-query-in-table-result")
    public ResponseEntity<Void> modifyTableQueryInTableResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @PutMapping("/modify-query-in-table")
    public ResponseEntity<Void> modifyTableQueryInTable(@RequestBody TableQuery tableQuery, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.updateQueryWithQueryId(tableQuery);
    }

    @PostMapping("/add-delete-table-query-by-id-result")
    public ResponseEntity<Void> deleteTableQueryByIdResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @DeleteMapping("/delete-table-query-by-id/{id}")
    public ResponseEntity<Void> deleteTableQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.deleteQueryById(id);
    }

    @PostMapping("/add-execute-table-query-by-id-result")
    public ResponseEntity<Void> executeTableQueryByIdResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @GetMapping("/execute-table-query-by-id/{id}")
    public ResponseEntity<Void> executeTableQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.executeTableQuery(id);
    }

    @PostMapping("/add-get-table-query-by-id-result")
    public ResponseEntity<Void> getTableQueryByIdResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @GetMapping("/get-table-query-by-id/{id}")
    public ResponseEntity<TableQuery> getTableQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.getQueryById(id);
    }

    @PostMapping("/add-get-all-table-queries-result")
    public ResponseEntity<Void> getAllTableQueriesResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @GetMapping("/get-all-table-queries")
    public List<TableQuery> getAllTableQueries(@RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.getAllQueries();
    }

    @PostMapping("/add-get-all-queries-by-table-name-result")
    public ResponseEntity<Void> getAllTableQueriesByNameResult(@RequestBody QueryResult queryResult) {
        return tableQueryService.addNewQueryResult(queryResult);
    }

    @GetMapping("/get-all-queries-by-table-name/{name}")
    public List<TableQuery> getAllTableQueriesByName(@PathVariable String name, @RequestParam(name="resultId") Integer resultId) {
        return tableQueryService.getAllQueriesByName(name);
    }
}
