package ru.vk.competition.minbenchmark.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vk.competition.minbenchmark.dto.report.Report;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReportValidation {

    public boolean isValid(Report report) {

        if(report.getReportId() == null) {
            log.error("ReportId null");
            return false;
        }

        if(report.getTableAmount() == null) {
            log.error("TableAmount null");
            return false;
        }

        if(report.getTables() == null) {
            log.error("report tables null");
            return false;
        }

        if(report.getTables().size() != report.getTableAmount()) {
            log.error("wrong size {} TableAmount {} ", report.getTables().size(), report.getTableAmount());
            return false;
        }
        return true;
    }
}
