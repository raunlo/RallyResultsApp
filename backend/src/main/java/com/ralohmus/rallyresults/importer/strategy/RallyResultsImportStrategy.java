package com.ralohmus.rallyresults.importer.strategy;

import com.ralohmus.rallyresults.importer.model.RallyImport;

import java.util.Set;

public interface RallyResultsImportStrategy {
    Set<RallyImport> findAllResults();
}
