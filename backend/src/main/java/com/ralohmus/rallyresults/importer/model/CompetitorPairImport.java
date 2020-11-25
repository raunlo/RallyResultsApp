package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompetitorPairImport {
    private String competitionClass;
    private CompetitorImport driver;
    private CompetitorImport coDriver;
}
