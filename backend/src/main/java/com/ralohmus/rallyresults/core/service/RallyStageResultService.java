package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.service.ports.application.CrudStageResultsPort;
import com.ralohmus.rallyresults.core.service.ports.persistence.StageResultsPersistencePort;
import com.ralohmus.rallyresults.persistence.exception.ResourceConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RallyStageResultService extends BaseService<StageResult, StageResultsPersistencePort> implements CrudStageResultsPort {

    RallyStageResultService(StageResultsPersistencePort persistencePort) {
        super(persistencePort);
    }

    @Override
    public Page<StageResult> getPage(Pageable pageable, Long stageId) {
       return persistencePort.getPage(pageable, stageId);
    }

    @Override
    public StageResult save(StageResult stageResult) {
        if(persistencePort.findByCompetitorAndStage(stageResult.getCompetitor().getId(), stageResult.getStage().getId()).isPresent()) {
            throw new ResourceConflictException("Stage already has result with given competitor");
        }
        return super.save(stageResult);
    }
}
