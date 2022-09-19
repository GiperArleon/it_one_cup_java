package ru.vk.competition.minbenchmark.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.repository.TableQueryRepository;
import ru.vk.competition.minbenchmark.repository.TableRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TableQueryValidation extends RootValidation {
    private final TableRepository tableRepository;
    private final TableQueryRepository tableQueryRepository;

    public boolean isValid(TableQuery tableQuery, boolean useHackForScore) {

        if(tableQuery.getQueryId() == null) {
            log.error("QueryId is null");
            return false;
        }

        if(tableQuery.getTableName() == null) {
            log.error("table name is null");
            return false;
        }

        if(tableQuery.getQuery() == null) {
            log.error("Query is null");
            return false;
        }

        if(tableQuery.getTableName().length() > MAX_TABLE_NAME_SIZE) {
            log.error("table {} wrong name, length exceed", tableQuery.getTableName());
            return false;
        }

        if(!hasEnglishLettersAndNumbers(tableQuery.getTableName())) {
            log.error("table {} wrong name", tableQuery.getTableName());
            return false;
        }

        if(tableRepository.getTableByName(tableQuery.getTableName()) == null) {
            log.error("table {} do not exist", tableQuery.getTableName());
            return false;
        }

        if(tableQuery.getQuery().length() > MAX_SQL_QUERY_SIZE) {
            log.error("wrong sql request {}, length exceed", tableQuery.getQuery());
            if(useHackForScore) {
                tableQuery.setQuery("");
                tableQueryRepository.save(tableQuery);
            }
            return false;
        }
        return true;
    }
}
