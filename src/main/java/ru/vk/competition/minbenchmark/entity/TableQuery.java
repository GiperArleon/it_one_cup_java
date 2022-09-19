package ru.vk.competition.minbenchmark.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "table_query", indexes = @Index(name = "tn_index", columnList = "tableName"))
@NoArgsConstructor
@AllArgsConstructor
public class TableQuery {
    @Id
    @Column(name = "queryId")
    private Integer queryId;

    @Column(name = "tableName")
    private String tableName;

    @Column(name = "query")
    private String query;
}
