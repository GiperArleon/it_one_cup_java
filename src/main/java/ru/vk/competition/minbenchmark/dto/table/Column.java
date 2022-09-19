package ru.vk.competition.minbenchmark.dto.table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Column {
    private String title;
    private String type;
}

