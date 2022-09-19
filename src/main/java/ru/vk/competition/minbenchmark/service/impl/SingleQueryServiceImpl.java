package ru.vk.competition.minbenchmark.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vk.competition.minbenchmark.dto.single.QueryResult;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import ru.vk.competition.minbenchmark.exceptions.QueryNotFoundException;
import ru.vk.competition.minbenchmark.exceptions.UpdateQueryNotFoundException;
import ru.vk.competition.minbenchmark.repository.SingleQueryRepository;
import ru.vk.competition.minbenchmark.repository.TableQueryRepository;
import ru.vk.competition.minbenchmark.service.SingleQueryService;
import ru.vk.competition.minbenchmark.sql.SQLParser;
import ru.vk.competition.minbenchmark.sql.SQLParserResponse;
import ru.vk.competition.minbenchmark.sql.StatementType;
import ru.vk.competition.minbenchmark.validation.SingleQueryValidation;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SingleQueryServiceImpl implements SingleQueryService {

    private final SingleQueryRepository queryRepository;
    private final TableQueryRepository tableQueryRepository;
    private final SingleQueryValidation singleQueryValidation;
    private final SQLParser sqlParser;

    @Transactional
    @Override
    public ResponseEntity<Void> addQueryWithQueryId(SingleQuery singleQuery) {
        if(!singleQueryValidation.isValid(singleQuery))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Optional<SingleQuery> tmp = queryRepository.findByQueryId(singleQuery.getQueryId());
        if(tmp.isPresent()) {
            log.error("query with id {} already exist, use put method for modification", singleQuery.getQueryId());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        queryRepository.save(singleQuery);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> addNewQueryResult(QueryResult singleQueryResult) {
        log.info("queryResult request: resultId = {}, code = {}", singleQueryResult.getResultId(), singleQueryResult.getCode());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> updateQueryWithQueryId(SingleQuery singleQuery) {
        if(!singleQueryValidation.isValid(singleQuery))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        queryRepository.findByQueryId(singleQuery.getQueryId())
                .orElseThrow(() -> new UpdateQueryNotFoundException(
                        String.format("Cannot find singleQuery by ID %s for update", singleQuery.getQueryId())
                ));
        queryRepository.save(singleQuery);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<Void> deleteQueryById(Integer id) {
        try {
            if(queryRepository.findByQueryId(id).map(SingleQuery::getQueryId).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            } else {
                queryRepository.deleteByQueryId(id);
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Void> executeSingleQuery(Integer id) {
        String sql = queryRepository.findByQueryId(id).map(SingleQuery::getQuery)
                .orElseThrow(() -> new UpdateQueryNotFoundException(
                        String.format("Cannot find query by ID %d for execute", id)
                ));

        SQLParserResponse sqlParserResponse;
        try {
            sqlParserResponse = sqlParser.ValidateAndfindTableNames(sql);
        } catch(Exception ex) {
            log.error("can not parse sql exp: {}", ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        try {
            queryRepository.executeQuery(sql);
            if(sqlParserResponse.getStatementType().equals(StatementType.DROP_TABLE) &&
                    !sqlParserResponse.getDropTableList().isEmpty()) {
                sqlParserResponse.getDropTableList().forEach(v -> {
                    List<TableQuery> queriesToDelete = tableQueryRepository.findByTableName(v);
                    if(!queriesToDelete.isEmpty()) {
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
            log.error("can not execute sql: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<SingleQuery> getQueryById(Integer id) {
        SingleQuery singleQuery = queryRepository.findByQueryId(id)
                .orElseThrow(() -> new QueryNotFoundException(
                        String.format("Cannot find singleQuery by ID %s", id)));
        return new ResponseEntity<>(singleQuery, HttpStatus.OK); //ResponseEntity.ok(singleQuery);
    }

    @Transactional(readOnly = true)
    @Override
    public List<SingleQuery> getAllQueries() {
        return queryRepository.findAll();
    }
}
