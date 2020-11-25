package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;

import java.util.List;

public interface FindCompetitorsByNamePort {
    List<CompetitorPair> getCompetitorPair(String name);
}
