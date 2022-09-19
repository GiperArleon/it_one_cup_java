package ru.vk.competition.minbenchmark.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.competition.minbenchmark.entity.TableQuery;
import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "TableQueryCache")
public interface TableQueryRepository extends JpaRepository<TableQuery, String>, JDBCExecutor {
    @Cacheable(key = "#id")
    Optional<TableQuery> findByQueryId(Integer id);

    @Cacheable(key = "#name")
    List<TableQuery> findByTableName(String name);

    @CacheEvict(allEntries = true)
    void deleteByQueryId(Integer id);

    @CacheEvict(allEntries = true)
    void deleteByTableName(String name);

    @CacheEvict(allEntries = true)
    TableQuery save(TableQuery singleQuery);
}
