package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.competitor.Competitor;

public interface CompetitorPersistencePort extends CrudPersistencePort<Competitor> {
   void saveCompetitorIfItDoesNotExist(Competitor competitor);
}
