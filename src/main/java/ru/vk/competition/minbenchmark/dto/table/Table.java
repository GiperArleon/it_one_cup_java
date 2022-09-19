package ru.vk.competition.minbenchmark.dto.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {
    private String tableName;
    private Integer columnsAmount;
    private String primaryKey;
    private List<Column> columnInfos;
}
