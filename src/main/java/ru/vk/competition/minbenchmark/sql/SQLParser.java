package ru.vk.competition.minbenchmark.sql;

import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.alter.AlterOperation;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SQLParser {

    public SQLParserResponse ValidateAndfindTableNames(String sql) throws JSQLParserException {
        Statement statement = CCJSqlParserUtil.parse(sql);
        SQLParserResponse sqlParserResponse = new SQLParserResponse();
        if(statement instanceof Drop) {
            Drop dropStatement = (Drop) statement;
            sqlParserResponse.setStatementType(StatementType.DROP_TABLE);
            TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
            sqlParserResponse.setDropTableList(tablesNamesFinder.getTableList(dropStatement));
            return sqlParserResponse;
        } else if(statement instanceof Alter) {
            Alter alterStatement = (Alter) statement;
            sqlParserResponse.setStatementType(StatementType.RENAME_TABLE);
            List<AlterExpression> alterExpressionsList = alterStatement.getAlterExpressions();
            List<RenameTable> renameTableList = new ArrayList<>();
            alterExpressionsList.forEach(v -> {
                if(v.getOperation().equals(AlterOperation.RENAME_TABLE)) {
                    renameTableList.add(new RenameTable(alterStatement.getTable().getName(), v.getNewTableName()));
                }
            });
            sqlParserResponse.setRenameTableList(renameTableList);
            return sqlParserResponse;
        }
        return sqlParserResponse;
    }
}
