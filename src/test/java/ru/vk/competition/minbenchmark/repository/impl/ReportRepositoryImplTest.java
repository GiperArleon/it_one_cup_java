package ru.vk.competition.minbenchmark.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.vk.competition.minbenchmark.dto.report.Report;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
//@DataJpaTest //поднимет контекст
class ReportRepositoryImplTest {
    private static final int ID = 1;

    @InjectMocks
    private ReportRepositoryImpl reportRepository;

    @Mock
    JDBCExecutorImpl jdbcExecutor;

    @Test
    void getReportById_shouldFindEvent_whenExists() {
        final Report report = mock(Report.class);
        when(report.getReportId()).thenReturn(ID);

        reportRepository.save(report);
        final Report actualReport = reportRepository.getReportById(ID).get();

        assertNotNull(actualReport);
        assertEquals(report, actualReport);
    }

    @Test
    void save_shouldSaveLastCalledEvent_whenCalledMultipleTimes() {
        final Report report = mock(Report.class);
        when(report.getReportId()).thenReturn(ID);
        final Report lastReport = mock(Report.class);
        when(lastReport.getReportId()).thenReturn(ID);

        reportRepository.save(report);
        reportRepository.save(lastReport);
        final Report actualReport = reportRepository.getReportById(ID).get();

        assertNotNull(actualReport);
        assertEquals(lastReport, actualReport);
    }

    @Test
    //@Sql({"/test-schema.sql", "/test-insert-data.sql"})
    void getColumnCount() {
         when(jdbcExecutor.queryForObject("SELECT COUNT(id) FROM ARTEM;", Integer.class)).thenReturn(33);
         when(jdbcExecutor.queryForObject("SELECT COUNT(name) FROM ARTEM;", Integer.class)).thenReturn(21);
         when(jdbcExecutor.queryForObject("SELECT COUNT(age) FROM ARTEM;", Integer.class)).thenReturn(4);

         Integer col1 = reportRepository.getColumnCount("id", "ARTEM");
         Integer col2 = reportRepository.getColumnCount("name", "ARTEM");
         Integer col3 = reportRepository.getColumnCount("age", "ARTEM");

         log.info("cols: {} {} {}", col1, col2, col3);
    }
}