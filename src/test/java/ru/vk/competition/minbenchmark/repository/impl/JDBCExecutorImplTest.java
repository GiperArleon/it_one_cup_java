package ru.vk.competition.minbenchmark.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import javax.sql.DataSource;

@Slf4j
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class JDBCExecutorImplTest {

    DataSource dataSource;
    JdbcTemplate jdbcTemplate;
    JDBCExecutorImpl jdbcExecutor;

    @BeforeEach
    void dataFill() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:test-schema.sql")
                .addScript("classpath:test-insert-data.sql")
                .build();

        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcExecutor = new JDBCExecutorImpl(jdbcTemplate);
    }

    @AfterEach
    void dataDelete() {
        jdbcExecutor.executeQuery("DROP TABLE ARTEM");
    }

    @Test
    void executeQuery() {
        String sql = "SELECT COUNT(id) FROM ARTEM;";
        int res = jdbcExecutor.queryForObject(sql, Integer.class);
        log.info("res = {}", res);
    }

    @Test
    void queryForObject() {
    }
}