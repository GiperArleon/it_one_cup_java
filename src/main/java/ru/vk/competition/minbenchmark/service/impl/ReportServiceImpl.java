package ru.vk.competition.minbenchmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.dto.report.TableReport;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.exceptions.ReportNotFoundException;
import ru.vk.competition.minbenchmark.repository.ReportRepository;
import ru.vk.competition.minbenchmark.repository.TableRepository;
import ru.vk.competition.minbenchmark.service.ReportService;
import ru.vk.competition.minbenchmark.validation.ReportValidation;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final TableRepository tableRepository;
    private final ReportValidation reportValidation;

    @Override
    public ResponseEntity<Void> addNewQueryResult(QueryResult queryResult) {
        log.info("queryResult request: resultId = {}, code = {}", queryResult.getResultId(), queryResult.getCode());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Report> getReportById(Integer id) {
        Report report = reportRepository.getReportById(id)
                .orElseThrow(() -> new ReportNotFoundException(
                        String.format("cannot find report by id = %s", id)));
        return new ResponseEntity<>(report, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> createReport(Report report) {
        if(!reportValidation.isValid(report))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Optional<Report> tmp = reportRepository.getReportById(report.getReportId());
        if(tmp.isPresent()) {
            log.error("report with id {} already exist", report.getReportId());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        for(TableReport val : report.getTables()) {
            if(tableRepository.getTableByName(val.getTableName()) == null) {
                log.error("table {} for report do not exist", val.getTableName());
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        }

        reportRepository.save(report);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
