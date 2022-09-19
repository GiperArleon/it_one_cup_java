package ru.vk.competition.minbenchmark.repository;

public interface JDBCExecutor {
    void executeQuery(String sql);
    Integer queryForObject(String sql, Class<Integer> requiredType);
}
