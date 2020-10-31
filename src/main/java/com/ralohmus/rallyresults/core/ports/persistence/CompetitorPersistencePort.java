package com.ralohmus.rallyresults.core.ports.persistence;

import com.ralohmus.rallyresults.core.domain.Competitor;

public interface CompetitorPersistencePort {
   void saveCompetitorIfItDoesNotExist(Competitor competitor);
}
