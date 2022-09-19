package ru.vk.competition.minbenchmark.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.service.ReportService;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/add-get-report-by-id-result")
    public ResponseEntity<Void> getReportByIdResult(@RequestBody QueryResult queryResult) {
        return reportService.addNewQueryResult(queryResult);
    }

    @GetMapping("/get-report-by-id/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Integer id, @RequestParam(name="resultId") Integer resultId) {
        return reportService.getReportById(id);
    }

    @PostMapping("/add-create-report-result")
    public ResponseEntity<Void> createReportResult(@RequestBody QueryResult queryResult) {
        return reportService.addNewQueryResult(queryResult);
    }

    @PostMapping("/create-report")
    public ResponseEntity<Void> createReport(@RequestBody Report report, @RequestParam(name="resultId") Integer resultId) {
        return reportService.createReport(report);
    }
}
