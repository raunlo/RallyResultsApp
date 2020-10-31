package com.ralohmus.rallyresults.importer.strategy;

import com.ralohmus.rallyresults.importer.model.CompetitionImport;

import java.util.Set;

public interface RallyResultsImportStrategy {
    Set<CompetitionImport> findAllResults();
}
