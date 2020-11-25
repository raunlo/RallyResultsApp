package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.service.ports.application.CrudCompetitorPairPort;
import com.ralohmus.rallyresults.core.service.ports.application.FindCompetitorsByNamePort;
import com.ralohmus.rallyresults.core.service.ports.application.FindCompetitorClassPort;
import com.ralohmus.rallyresults.core.service.ports.persistence.CompetitorPairPersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitorPairService extends BaseService<CompetitorPair, CompetitorPairPersistencePort> implements CrudCompetitorPairPort, FindCompetitorsByNamePort, FindCompetitorClassPort {

    CompetitorPairService(CompetitorPairPersistencePort competitorPairPersistencePort) {
        super(competitorPairPersistencePort);
    }

    @Override
    public List<CompetitorPair> getCompetitorPair(String name) {
        return super.persistencePort.findCompetitorPairByName(name);
    }

    @Override
    public List<String> getAllCompetitorClasses(String name) {
       return persistencePort.getAllCompetitorClasses(name);
    }


}
