package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StageResultsImport {
    private CompetitorImport driver;
    private CompetitorImport coDriver;
    private StageResultsImport stage;
}
