package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;
import java.util.List;

public interface StageResultsPersistencePort extends CrudPersistencePort<StageResult>, PagePersistencePort<StageResult> {
   boolean existsStageWithCompetitorId(Long id);

   Set<StageResult> findStageResultByCompetitorPairIdsAndStageIds(Set<Long> stageIds, Set<Long> competitorIds);

   Page<StageResult> getPage(Pageable pageable, Long stageId);

   Optional<StageResult> findStageResult(StageResult stageResult);

   List<StageResult> findAllStageResultsByStageId(Long stageId);

   Optional<StageResult> findByCompetitorAndStage(Long competitorId, Long stageId);
}
