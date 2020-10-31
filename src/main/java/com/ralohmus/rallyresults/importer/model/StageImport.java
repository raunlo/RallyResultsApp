package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StageImport {

    private String name;
    private Double length;
    private StageResultsImport stageResultsImport;
}
