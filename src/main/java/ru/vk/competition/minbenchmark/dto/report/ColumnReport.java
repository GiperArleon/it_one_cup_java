package ru.vk.competition.minbenchmark.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnReport {
    private String title;
    private String type;
    private Integer size;
}
