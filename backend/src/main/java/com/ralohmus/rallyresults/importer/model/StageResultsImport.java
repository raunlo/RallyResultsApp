package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StageResultsImport {
    private StageImport stage;
    private String time;
    private CompetitorPairImport competitorPairImport;
}
