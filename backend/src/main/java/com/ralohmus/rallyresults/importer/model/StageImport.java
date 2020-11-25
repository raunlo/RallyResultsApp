package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Accessors(chain = true)
public class StageImport {

    private String name;
    private BigDecimal length;
    private Integer stageNumber;
    private Set<StageResultsImport> stageResultsImport;
}
