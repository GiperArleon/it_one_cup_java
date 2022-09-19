package ru.vk.competition.minbenchmark.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.service.ReportService;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReportControllerTest {
    private final static int ID = 1;
    private final static int testID = 1;

    @Mock
    ReportService reportService;

    @InjectMocks
    ReportController reportController;

    @Test
    void getReportById() {
        Optional<Report> reportOptional = Optional.of(mock(Report.class));
        final Report report = reportOptional.get();
        assertNotNull(report);
        ResponseEntity<Report> responseEntity = new ResponseEntity<>(report, HttpStatus.CREATED);
        when(reportService.getReportById(ID)).thenReturn(responseEntity);

        final ResponseEntity<Report> actualEntity = reportController.getReportById(ID, testID);

        assertEquals(responseEntity, actualEntity);
        verify(reportService).getReportById(ID);
    }

    @Test
    void createReport() {
        final Report report = mock(Report.class);
        when(reportService.createReport(report)).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        reportController.createReport(report, testID);

        verify(reportService).createReport(report);
    }
}