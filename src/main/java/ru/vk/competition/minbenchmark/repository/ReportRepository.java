package ru.vk.competition.minbenchmark.repository;

import ru.vk.competition.minbenchmark.dto.report.Report;
import java.util.Optional;

public interface ReportRepository {
    public Optional<Report> getReportById(Integer id);
    public void save(Report report);
}
