package ru.vk.competition.minbenchmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.exceptions.QueryNotFoundException;
import ru.vk.competition.minbenchmark.exceptions.UpdateQueryNotFoundException;
import ru.vk.competition.minbenchmark.repository.TableQueryRepository;
import ru.vk.competition.minbenchmark.service.TableQueryService;
import ru.vk.competition.minbenchmark.sql.SQLParser;
import ru.vk.competition.minbenchmark.sql.SQLParserResponse;
import ru.vk.competition.minbenchmark.sql.StatementType;
import ru.vk.competition.minbenchmark.validation.TableQueryValidation;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TableQueryServiceImpl implements TableQueryService {

    private final TableQueryRepository tableQueryRepository;
    private final TableQueryValidation tableQueryValidation;
    private final SQLParser sqlParser;

    @Override
    public ResponseEntity<Void> addNewQueryResult(QueryResult queryResult) {
        log.info("queryResult request: resultId = {}, code = {}", queryResult.getResultId(), queryResult.getCode());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> addQueryWithQueryId(TableQuery tableQuery) {
        if(!tableQueryValidation.isValid(tableQuery, false))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Optional<TableQuery> tmp = tableQueryRepository.findByQueryId(tableQuery.getQueryId());
        if(tmp.isPresent()) {
            log.error("query with id {} already exist, use put method for modification", tableQuery.getQueryId());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        tableQueryRepository.save(tableQuery);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> updateQueryWithQueryId(TableQuery tableQuery) {
        if(!tableQueryValidation.isValid(tableQuery, true))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        tableQueryRepository.findByQueryId(tableQuery.getQueryId())
                .orElseThrow(() -> new UpdateQueryNotFoundException(
                        String.format("Cannot find tableQuery by ID %s for update", tableQuery.getQueryId())
                ));
        tableQueryRepository.save(tableQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteQueryById(Integer id) {
        try {
            if(tableQueryRepository.findByQueryId(id).map(TableQuery::getQueryId).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                tableQueryRepository.deleteByQueryId(id);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Void> executeTableQuery(Integer id) {
        String sql = tableQueryRepository.findByQueryId(id).map(TableQuery::getQuery)
                .orElseThrow(() -> new UpdateQueryNotFoundException(
                        String.format("Cannot find tableQuery by ID %d for execute", id)
                ));

        SQLParserResponse sqlParserResponse;
        try {
            sqlParserResponse = sqlParser.ValidateAndfindTableNames(sql);
        } catch(Exception ex) {
            log.error("can not parse sql exp: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            tableQueryRepository.executeQuery(sql);
            if(sqlParserResponse.getStatementType().equals(StatementType.DROP_TABLE) &&
                    !sqlParserResponse.getDropTableList().isEmpty()) {
                sqlParserResponse.getDropTableList().forEach(v -> {
                    List<TableQuery> list = tableQueryRepository.findByTableName(v);
                    if(!list.isEmpty()) {
                        tableQueryRepository.deleteByTableName(v);
                    }
                });
            } else if(sqlParserResponse.getStatementType().equals(StatementType.RENAME_TABLE) &&
                    !sqlParserResponse.getRenameTableList().isEmpty()) {
                sqlParserResponse.getRenameTableList().forEach(v -> {
                    List<TableQuery> queriesToRename = tableQueryRepository.findByTableName(v.getOldTableName());
                    if(!queriesToRename.isEmpty()) {
                        queriesToRename.forEach(q -> {
                            q.setTableName(v.getNewTableName());
                            tableQueryRepository.save(q);
                        });
                    }
                });
            }
        } catch(Exception e) {
            log.error(e.getClass().getName() + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<TableQuery> getQueryById(Integer id) {
        TableQuery tableQuery = tableQueryRepository.findByQueryId(id)
                .orElseThrow(() -> new QueryNotFoundException(
                        String.format("Cannot find tableQuery by ID %s", id)));
        return ResponseEntity.ok(tableQuery);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TableQuery> getAllQueries() {
        return tableQueryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TableQuery> getAllQueriesByName(String name) {
        return tableQueryRepository.findByTableName(name);
    }
}
