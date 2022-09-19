package ru.vk.competition.minbenchmark.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.vk.competition.minbenchmark.dto.table.Table;
import ru.vk.competition.minbenchmark.repository.JDBCExecutor;
import ru.vk.competition.minbenchmark.repository.TableRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class TableRepositoryImpl implements TableRepository {

    private final ConcurrentHashMap<String, Table> cache = new ConcurrentHashMap<>();
    private final JDBCExecutor jdbcExecutor;

    @Override
    public void createTable(Table table) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> columns = new ArrayList<>();

        table.getColumnInfos().forEach(c -> {
            columns.add(c.getTitle() + " " + c.getType());
            c.setTitle(c.getTitle().toUpperCase());
            if(c.getType().toUpperCase().contains("VARCHAR")) {
                c.setType("CHARACTER VARYING");
            } else if(c.getType().toUpperCase().contains("INT")) {
                c.setType("INTEGER");
            } else {
                c.setType(c.getType().toUpperCase());
            }
        });

        stringBuilder
                .append("CREATE TABLE ")
                .append(table.getTableName())
                .append(" (")
                .append(String.join(",", columns))
                .append(", PRIMARY KEY (")
                .append(table.getPrimaryKey())
                .append("));");
        jdbcExecutor.executeQuery(stringBuilder.toString());

        table.setPrimaryKey(table.getPrimaryKey().toLowerCase());
        cache.put(table.getTableName(), table);
    }

    @Override
    public Table getTableByName(String name) {
        return cache.get(name);
    }

    @Override
    public void dropTableByName(String name) {
        jdbcExecutor.executeQuery("DROP TABLE " + name);
        cache.remove(name);
    }
}
