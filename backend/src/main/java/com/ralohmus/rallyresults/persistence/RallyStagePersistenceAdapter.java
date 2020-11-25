package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyStagePersistencePort;
import com.ralohmus.rallyresults.persistence.entities.rally.RallyDbo_;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo_;
import com.ralohmus.rallyresults.persistence.mapper.RallyStageToStageDboMapper;
import com.ralohmus.rallyresults.persistence.mapper.StageDboToRallyStageMapper;
import com.ralohmus.rallyresults.persistence.repo.StageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RallyStagePersistenceAdapter extends BasePersistenceAdapter<RallyStage, StageDbo> implements RallyStagePersistencePort {
    RallyStagePersistenceAdapter(StageRepository repository,
                                 StageDboToRallyStageMapper stageDboToRallyStageMapper,
                                 RallyStageToStageDboMapper rallystageToStageDboMapper) {
        super(repository, stageDboToRallyStageMapper, rallystageToStageDboMapper);
    }

    @Override
    public Page<RallyStage> getPage(Pageable pageable, Long rallyId) {
        var jpaFilter = Specification
                .<StageDbo>where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(StageDbo_.RALLY).get(RallyDbo_.ID), rallyId));

        return repository.findAll(jpaFilter, pageable).map(this.dboToDomainMapper::map);
    }

    @Override
    public boolean existsByRallyAndStageNumber(Long rallyId, Short stageId) {
        var jpaFilter = Specification.<StageDbo>where((root, query, cb) -> cb.equal(root.get(StageDbo_.STAGE_NUMBER), stageId))
                .and((root, query, cb) -> cb.equal(root.get(StageDbo_.RALLY).get(RallyDbo_.ID), rallyId));

        return repository.findOne(jpaFilter).isPresent();
    }

    @Override
    public Optional<RallyStage> findRallyStage(RallyStage rallyStage, Long rallyId) {
        var filter = Specification.<StageDbo>where((root, query, cb) ->
                cb.equal(root.get(StageDbo_.TRACK_NAME), rallyStage.getTrackName()))
                .and((root, query, cb) ->
                        cb.equal(root.get(StageDbo_.STAGE_NUMBER), rallyStage.getStageNumber()))
                .and((root, query, cb) ->
                    cb.equal(root.get(StageDbo_.LENGTH), rallyStage.getLength()))
                .and((root, query, cb) -> cb.equal(root.get(StageDbo_.RALLY).get(RallyDbo_.ID), rallyId));

        return repository.findOne(filter).map(dboToDomainMapper::map);

    }
}
