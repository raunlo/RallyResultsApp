package com.ralohmus.rallyresults.importer;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.service.ports.importer.RallyResultsImporterPort;
import com.ralohmus.rallyresults.importer.mapper.CompetitorImportToCompetitorMapper;
import com.ralohmus.rallyresults.importer.strategy.RallyResultsImportStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RallyResultsImporterAdapter implements RallyResultsImporterPort {

    private final RallyResultsImportStrategy strategy;
    private final CompetitorImportToCompetitorMapper competitorImportToCompetitorMapper;

    @Override
    public List<Rally> importData() {
        return strategy.findAllResults().stream().filter(s -> !CollectionUtils.isEmpty(s.getStages()))
                .map(competitorImportToCompetitorMapper::map)
                .collect(Collectors.toUnmodifiableList());
    }

}
