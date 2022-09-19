package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import ru.vk.competition.minbenchmark.service.SingleQueryService;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/single-query")
@RequiredArgsConstructor
public class SingleQueryController {

    private final SingleQueryService singleQueryService;

    @PostMapping("/add-new-query-result")
    public ResponseEntity<Void> addNewQueryResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @PostMapping("/add-new-query")
    public ResponseEntity<Void> addNewQueryToTableWithTestId(@RequestParam(name="resultId") Integer resultId, @RequestBody SingleQuery singleQuery) {
        log.info("add-new-query, test id = {}", resultId);
        return singleQueryService.addQueryWithQueryId(singleQuery);
    }

    @PostMapping("/add-get-single-query-by-id-result")
    public ResponseEntity<Void> addGetSingleQueryByIdResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @GetMapping("/get-single-query-by-id/{id}")
    public ResponseEntity<SingleQuery> getSingleQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return singleQueryService.getQueryById(id);
    }

    @PostMapping("/add-get-all-single-queries-result")
    public ResponseEntity<Void> addGetAllSingleQueriesResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @GetMapping("/get-all-single-queries")
    public List<SingleQuery> getAllSingleQueries(@RequestParam(name="resultId") Integer resultId) {
        return singleQueryService.getAllQueries();
    }

    @PostMapping("/add-modify-result")
    public ResponseEntity<Void> addModifySingleQueryResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @PutMapping("/modify-single-query")
    public ResponseEntity<Void> modifySingleQuery(@RequestBody SingleQuery singleQuery, @RequestParam(name="resultId") Integer resultId) {
        return singleQueryService.updateQueryWithQueryId(singleQuery);
    }

    @PostMapping("/add-delete-result")
    public ResponseEntity<Void> addDeleteSingleQueryByIdResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @DeleteMapping("/delete-single-query-by-id/{id}")
    public ResponseEntity<Void> deleteSingleQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return singleQueryService.deleteQueryById(id);
    }

    @PostMapping("/add-execute-single-query-by-id")
    public ResponseEntity<Void> addExecuteSingleQueryByIdResult(@RequestBody QueryResult singleQueryResult) {
        return singleQueryService.addNewQueryResult(singleQueryResult);
    }

    @GetMapping("/execute-single-query-by-id/{id}")
    public ResponseEntity<Void> executeSingleQueryById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return singleQueryService.executeSingleQuery(id);
    }
}