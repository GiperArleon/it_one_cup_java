package ru.vk.competition.minbenchmark.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.dto.report.ColumnReport;
import ru.vk.competition.minbenchmark.dto.report.Report;
import ru.vk.competition.minbenchmark.dto.report.TableReport;
import ru.vk.competition.minbenchmark.exceptions.QueryCountException;
import ru.vk.competition.minbenchmark.repository.JDBCExecutor;
import ru.vk.competition.minbenchmark.repository.ReportRepository;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class ReportRepositoryImpl implements ReportRepository {
    public static ConcurrentHashMap<Integer, Report> cache = new ConcurrentHashMap<>();
    private final JDBCExecutor jdbcExecutor;

    @Override
    public Optional<Report> getReportById(Integer id) {
        Report report = cache.get(id);
        if(report == null)
            return Optional.empty();

        for(TableReport var: report.getTables()) {
            for(ColumnReport col: var.getColumns()) {
                col.setSize(getColumnCount(col.getTitle(), var.getTableName()));
            }
        }
        return Optional.of(report);
    }

    @Override
    public void save(Report report) {
        cache.put(report.getReportId(), report);
    }

    @Transactional(readOnly = true)
    Integer getColumnCount(String colName, String tableName) {
        String sql = String.format("SELECT COUNT(%s) FROM %s;", colName, tableName);
        try {
            return jdbcExecutor.queryForObject(sql, Integer.class);
        } catch (Exception ex) {
            throw new QueryCountException(ex.getMessage());
        }
    }
}
