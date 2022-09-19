package ru.vk.competition.minbenchmark.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.vk.competition.minbenchmark.entity.SingleQuery;
import java.util.Optional;

@CacheConfig(cacheNames = "SingleQueryCache")
public interface SingleQueryRepository extends JpaRepository<SingleQuery, String>, JDBCExecutor {
    @Cacheable(key = "#id")
    Optional<SingleQuery> findByQueryId(Integer id);

    @CacheEvict(allEntries = true)
    void deleteByQueryId(Integer id);

    @CacheEvict(allEntries = true)
    SingleQuery save(SingleQuery singleQuery);
}
