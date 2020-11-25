package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.service.ports.application.CrudRallyPort;
import com.ralohmus.rallyresults.core.service.ports.application.ImportRalliesPort;
import com.ralohmus.rallyresults.core.service.ports.application.SearchRalliesPort;
import com.ralohmus.rallyresults.core.service.ports.importer.RallyResultsImporterPort;
import com.ralohmus.rallyresults.core.service.ports.persistence.CompetitorPairPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyStagePersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.StageResultsPersistencePort;
import com.ralohmus.rallyresults.persistence.exception.ResourceConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class RallyService extends BaseService<Rally, RallyPersistencePort> implements CrudRallyPort, ImportRalliesPort, SearchRalliesPort {

    private final RallyStagePersistencePort rallyStagePersistencePort;
    private final CompetitorPairPersistencePort competitorPairPersistencePort;
    private final StageResultsPersistencePort stageResultsPersistencePort;
    private final RallyResultsImporterPort rallyResultsImporterPort;

    public RallyService(RallyPersistencePort rallyPersistencePort, RallyStagePersistencePort rallyStagePersistencePort,
                        CompetitorPairPersistencePort competitorPairPersistencePort,
                        StageResultsPersistencePort stageResultsPersistencePort, RallyResultsImporterPort rallyResultsImporterPort) {
        super(rallyPersistencePort);
        this.rallyStagePersistencePort = rallyStagePersistencePort;
        this.competitorPairPersistencePort = competitorPairPersistencePort;
        this.stageResultsPersistencePort = stageResultsPersistencePort;
        this.rallyResultsImporterPort = rallyResultsImporterPort;
    }

    @PostConstruct
    public void startup() {
        CompletableFuture.runAsync(this::importAndSaveRallies).exceptionally((err) -> {
            log.error(err.getMessage());
            return null;
        });
    }

    @Override
    @Transactional
    @Async("threadPoolTaskExecutor")
    @Scheduled(cron = "${rallies.import.cron}")
    public void importAndSaveRallies() {
        log.info("Import started");
        rallyResultsImporterPort.importData().forEach(rally -> {
            var savedRally = persistencePort.findRally(rally)
                    .orElseGet(() -> persistencePort.save(rally));

            saveRallyStages(rally.getRallyStages(), savedRally);
        });
        log.info("Import finished");
    }

    private void saveRallyStages(List<RallyStage> rallyStages, Rally rally) {
        rallyStages.forEach(rallyStage -> {
            var savedRallyStage = rallyStagePersistencePort.findRallyStage(rallyStage, rally.getId())
                    .orElseGet(() -> rallyStagePersistencePort.save(rallyStage.setRally(rally)));

            importRallyStagesResults(rallyStage.getStageResults(), savedRallyStage);
        });
    }

    private void importRallyStagesResults(List<StageResult> stageResults, RallyStage rallyStage) {
        stageResults.forEach(result -> {
            var savedCompetitor = competitorPairPersistencePort.findOrSaveCompetitorPair(result.getCompetitor());

            result.setCompetitor(savedCompetitor).setStage(rallyStage);

            stageResultsPersistencePort.findStageResult(result)
                    .ifPresentOrElse((stageResult) -> {
                    }, () -> stageResultsPersistencePort.save(result));
        });
    }

    @Override
    public List<Rally> searchRallies(String name) {
        var ralliesWthStages = persistencePort.findByRallyName(name);
        ralliesWthStages.forEach(rally -> rally.getRallyStages().sort(Comparator.comparing(RallyStage::getStageNumber)));
        return ralliesWthStages;
    }

    @Override
    public Rally save(Rally rally) {
        if (persistencePort.findRally(rally.getName(), rally.getStart(), rally.getEnd()).isPresent()) {
            throw new ResourceConflictException("Cannot save rally if name, start date and end date are same");
        }
        return super.save(rally);
    }
}

