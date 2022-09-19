package ru.vk.competition.minbenchmark.service;

import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;

public interface ReportService {
    ResponseEntity<Report> getReportById(Integer id);
    ResponseEntity<Void> createReport(Report report);
    ResponseEntity<Void> addNewQueryResult(QueryResult queryResult);
}
