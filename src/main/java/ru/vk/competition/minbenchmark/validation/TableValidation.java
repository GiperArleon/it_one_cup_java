package ru.vk.competition.minbenchmark.validation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.vk.competition.minbenchmark.dto.table.Column;
import ru.vk.competition.minbenchmark.dto.table.Table;
import ru.vk.competition.minbenchmark.repository.TableRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class TableValidation extends RootValidation {

    private final TableRepository tableRepository;

    public boolean isValid(Table table) {

        if(table.getTableName() == null) {
            log.error("table null name");
            return false;
        }

        if(table.getColumnsAmount() == null) {
            log.error("ColumnsAmount null");
            return false;
        }

        if(table.getColumnInfos() == null) {
            log.error("ColumnsAmount null");
            return false;
        }

        if(table.getPrimaryKey() == null) {
            log.error("table {} do not have PrimaryKey", table.getTableName());
            return false;
        }

        if(table.getTableName().length() > MAX_TABLE_NAME_SIZE) {
            log.error("table {} wrong name, length exceed", table.getTableName());
            return false;
        }

        if(!hasEnglishLettersAndNumbers(table.getTableName())) {
            log.error("table {} wrong name", table.getTableName());
            return false;
        }

        if(tableRepository.getTableByName(table.getTableName()) != null) {
            log.error("table {} already exist", table.getTableName());
            return false;
        }

        if(table.getColumnInfos().isEmpty()) {
            log.error("table {} do not have columns", table.getTableName());
            return false;
        }

        if(table.getColumnInfos().size() != table.getColumnsAmount()) {
            log.error("Wrong size ColumnsAmount = {}, list size = {}", table.getColumnsAmount(), table.getColumnInfos().size());
            return false;
        }

        for(Column val : table.getColumnInfos()) {
            if(val == null) {
                log.error("Column is null");
                return false;
            }

            if(!hasEnglishLettersAndNumbersAndBracers(val.getType()) || !hasEnglishLettersOnly(val.getTitle())) {
                log.error("table {} wrong title {} or type {}", table.getTableName(), val.getTitle(), val.getType());
                return false;
            }
        }

        Column column = table.getColumnInfos().stream()
                .filter(x -> table.getPrimaryKey().equals(x.getTitle()))
                .findAny()
                .orElse(null);
        if(column == null) {
            log.error("primary key {} not found in columns", table.getPrimaryKey());
            return false;
        }
        return true;
    }
}
