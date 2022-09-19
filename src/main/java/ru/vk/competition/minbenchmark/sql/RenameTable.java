package ru.vk.competition.minbenchmark.sql;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RenameTable {
    private final String oldTableName;
    private final String newTableName;
}
