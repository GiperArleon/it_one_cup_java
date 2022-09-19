package ru.vk.competition.minbenchmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.dto.table.Table;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.repository.TableQueryRepository;
import ru.vk.competition.minbenchmark.repository.TableRepository;
import ru.vk.competition.minbenchmark.service.TableService;
import ru.vk.competition.minbenchmark.validation.TableValidation;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableQueryRepository tableQueryRepository;
    private final TableRepository tableRepository;
    private final TableValidation tableValidation;

    @Override
    public ResponseEntity<Void> addNewQueryResult(QueryResult queryResult) {
        log.info("queryResult request: resultId = {}, code = {}", queryResult.getResultId(), queryResult.getCode());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> createTable(Table table) {
        if(!tableValidation.isValid(table))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        try {
            tableRepository.createTable(table);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(Exception e) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    public ResponseEntity<Table> getTableByName(String name) {
        Table table = tableRepository.getTableByName(name);
        return ResponseEntity.ok(table);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> dropTableByName(String name) {
        try {
            if(tableRepository.getTableByName(name) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                tableRepository.dropTableByName(name);
                List<TableQuery> list = tableQueryRepository.findByTableName(name);
                if(!list.isEmpty()) {
                    tableQueryRepository.deleteByTableName(name);
                }
                return new ResponseEntity<>(HttpStatus.CREATED);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
