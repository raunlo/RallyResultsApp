package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;

import java.util.List;

public interface CompetitorPairPersistencePort extends CrudPersistencePort<CompetitorPair>, PagePersistencePort<CompetitorPair> {
    List<CompetitorPair> findCompetitorPairByName(String name);

    CompetitorPair findOrSaveCompetitorPair(CompetitorPair competitorPair);

    List<String> getAllCompetitorClasses(String name);
}
