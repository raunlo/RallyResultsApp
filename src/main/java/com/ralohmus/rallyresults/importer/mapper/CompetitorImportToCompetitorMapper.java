package com.ralohmus.rallyresults.importer.mapper;

import com.ralohmus.rallyresults.core.domain.Competitor;
import com.ralohmus.rallyresults.importer.model.CompetitorImport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitorImportToCompetitorMapper extends BaseMapper<CompetitorImport, Competitor> {

    CompetitorImport map(Competitor competitor);
}
