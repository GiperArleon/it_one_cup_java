package ru.vk.competition.minbenchmark.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.vk.competition.minbenchmark.repository.JDBCExecutor;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class JDBCExecutorImpl implements JDBCExecutor {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void executeQuery(String sql) {
            jdbcTemplate.execute(sql);
    }

    @Override
    public Integer queryForObject(String sql, Class<Integer> requiredType) {
        return jdbcTemplate.queryForObject(sql, requiredType);
    }
}
