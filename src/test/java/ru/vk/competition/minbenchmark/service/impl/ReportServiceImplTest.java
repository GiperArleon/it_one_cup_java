package ru.vk.competition.minbenchmark.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.dto.report.TableReport;
import ru.vk.competition.minbenchmark.dto.table.Table;
import ru.vk.competition.minbenchmark.repository.ReportRepository;
import ru.vk.competition.minbenchmark.repository.TableRepository;
import ru.vk.competition.minbenchmark.validation.ReportValidation;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {
    private static final int ID = 1;
    @Mock
    ReportRepository reportRepository;
    @Mock
    TableRepository tableRepository;
    @Mock
    ReportValidation reportValidation;
    @InjectMocks
    ReportServiceImpl reportService;

    @Test
    void getReportById_shouldCall_ReportRepository() {
        Optional<Report> reportOptional = Optional.of(mock(Report.class));
        final Report report = reportOptional.get();
        assertNotNull(report);
        when(reportRepository.getReportById(ID)).thenReturn(reportOptional);
        ResponseEntity<Report> responseEntity = new ResponseEntity<>(report, HttpStatus.CREATED);

        final ResponseEntity<Report> actual = reportService.getReportById(ID);

        assertNotNull(actual);
        assertEquals(responseEntity, actual);
        verify(reportRepository).getReportById(ID);
    }

    @Test
    void createReport_shouldCall_ReportValidation_ReportRepository_andTableRepository() {
        Optional<Report> reportOptional = Optional.of(mock(Report.class));
        final Report report = reportOptional.get();
        assertNotNull(report);
        TableReport tableReport = mock(TableReport.class);
        Table table = mock(Table.class);
        List<TableReport> tableReportList = List.of(tableReport, tableReport, tableReport);

        when(reportValidation.isValid(report)).thenReturn(true);
        when(report.getReportId()).thenReturn(ID);
        when(report.getTables()).thenReturn(tableReportList);
        when(tableReport.getTableName()).thenReturn("ARTEM");
        when(reportRepository.getReportById(ID)).thenReturn(Optional.empty());
        when(tableRepository.getTableByName(tableReport.getTableName())).thenReturn(table);

        reportService.createReport(report);

        verify(reportValidation).isValid(report);
        verify(reportRepository).getReportById(ID);
        verify(tableRepository, times(tableReportList.size())).getTableByName(tableReport.getTableName());
    }
}