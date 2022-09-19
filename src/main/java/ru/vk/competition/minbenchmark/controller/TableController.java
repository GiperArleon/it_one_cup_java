package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.dto.table.Table;
import ru.vk.competition.minbenchmark.service.TableService;

@RestController
@RequestMapping("/api/table")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    @PostMapping("/add-create-table-result")
    public ResponseEntity<Void> createTableResult(@RequestBody QueryResult singleQueryResult) {
        return tableService.addNewQueryResult(singleQueryResult);
    }

    @PostMapping("/create-table")
    public ResponseEntity<Void> createTable(@RequestBody Table table, @RequestParam(name="resultId") Integer resultId) {
        return tableService.createTable(table);
    }

    @PostMapping("/add-get-table-by-name-result")
    public ResponseEntity<Void> getTableByNameResult(@RequestBody QueryResult singleQueryResult) {
        return tableService.addNewQueryResult(singleQueryResult);
    }

    @GetMapping("/get-table-by-name/{name}")
    public ResponseEntity<Table> getTableByName(@PathVariable String name, @RequestParam(name="resultId") Integer resultId) {
        return tableService.getTableByName(name);
    }

    @PostMapping("/add-drop-table-result")
    public ResponseEntity<Void> dropTableByNameResult(@RequestBody QueryResult singleQueryResult) {
        return tableService.addNewQueryResult(singleQueryResult);
    }

    @DeleteMapping("/drop-table/{name}")
    public ResponseEntity<Void> dropTableByName(@PathVariable("name") String name, @RequestParam(name="resultId") Integer resultId) {
        return tableService.dropTableByName(name);
    }
}
