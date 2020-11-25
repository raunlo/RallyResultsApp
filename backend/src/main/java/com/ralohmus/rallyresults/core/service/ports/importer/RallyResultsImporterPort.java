package com.ralohmus.rallyresults.core.service.ports.importer;

import com.ralohmus.rallyresults.core.domain.rally.Rally;

import java.util.List;

public interface RallyResultsImporterPort {
    List<Rally> importData();
}
