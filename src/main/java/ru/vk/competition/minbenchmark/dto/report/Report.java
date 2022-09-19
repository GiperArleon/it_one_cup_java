package ru.vk.competition.minbenchmark.dto.report;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private Integer reportId;
    private Integer tableAmount;
    private List<TableReport> tables;
}
