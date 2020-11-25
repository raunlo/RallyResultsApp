package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.service.ports.application.CrudRallyPort;
import com.ralohmus.rallyresults.core.service.ports.application.CrudRallyStagePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyStagePersistencePort;
import com.ralohmus.rallyresults.persistence.exception.ResourceConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class RallyStageService extends BaseService<RallyStage, RallyStagePersistencePort> implements CrudRallyStagePort {

    private final CrudRallyPort crudRallyPort;

    public RallyStageService(RallyStagePersistencePort rallyStagePersistencePort, CrudRallyPort crudRallyPort) {
        super(rallyStagePersistencePort);
        this.crudRallyPort = crudRallyPort;
    }

    @Override
    public RallyStage save(RallyStage rallyStage) {
        if(persistencePort.existsByRallyAndStageNumber(rallyStage.getRally().getId(), rallyStage.getStageNumber()))
            throw new ResourceConflictException("cannot save stage number is duplicate");
        var rally = rallyStage.setRally(crudRallyPort.findById(rallyStage.getRally().getId()).orElseThrow());
        return super.save(rally);
    }

    @Override
    public Page<RallyStage> getPage(Pageable pageable, Long rallyId) {
       return super.persistencePort.getPage(pageable, rallyId);
    }
}
