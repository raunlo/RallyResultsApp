package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RallyStagePersistencePort extends CrudPersistencePort<RallyStage>, PagePersistencePort<RallyStage> {

  Page<RallyStage> getPage(Pageable pageable, Long rallyId);

  boolean existsByRallyAndStageNumber(Long rallyId, Short stageId);

  Optional<RallyStage> findRallyStage(RallyStage rallyStage, Long rallyId);
}
