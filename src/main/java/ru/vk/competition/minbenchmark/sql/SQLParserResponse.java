package ru.vk.competition.minbenchmark.sql;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class SQLParserResponse {
    private StatementType statementType = StatementType.NOT_DEFINED;
    private List<String> dropTableList = new ArrayList<>();
    private List<RenameTable> renameTableList = new ArrayList<>();
}
