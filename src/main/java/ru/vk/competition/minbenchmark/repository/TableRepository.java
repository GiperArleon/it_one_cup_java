package ru.vk.competition.minbenchmark.repository;

import ru.vk.competition.minbenchmark.dto.table.Table;

public interface TableRepository {
    void createTable(Table table);
    Table getTableByName(String name);
    void dropTableByName(String name);
}
