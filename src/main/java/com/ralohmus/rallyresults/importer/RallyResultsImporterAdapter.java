package com.ralohmus.rallyresults.importer;

import com.ralohmus.rallyresults.core.ports.importer.RallyResultsImporterPort;
import com.ralohmus.rallyresults.core.ports.persistence.CompetitorPersistencePort;
import com.ralohmus.rallyresults.importer.mapper.CompetitorImportToCompetitorMapper;
import com.ralohmus.rallyresults.importer.model.CompetitionImport;
import com.ralohmus.rallyresults.importer.strategy.RallyResultsImportStrategy;
import com.ralohmus.rallyresults.importer.strategy.impl.ResultsDataWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.function.Consumer;

@Component
@Transactional
@RequiredArgsConstructor
public class RallyResultsImporterAdapter implements RallyResultsImporterPort {

    private final RallyResultsImportStrategy strategy;
    private final CompetitorImportToCompetitorMapper competitorImportToCompetitorMapper;
    private final CompetitorPersistencePort competitorPersistencePort;

    @Override
    public void importData() {
        Set<CompetitionImport> allResults = strategy.findAllResults();
//        allResults.forEach(this::mapResults);
    }

//    private Consumer<? super CompetitionImport> mapResults(ResultsDataWrapper resultsDataWrapper) {
//        if(resultsDataWrapper.isDataTypeOfCompetitor()) {
//           var competitor =  competitorImportToCompetitorMapper.map(resultsDataWrapper.getCompetitor());
//           competitorPersistencePort.saveCompetitorIfItDoesNotExist(competitor);
//        }
//    }
}
