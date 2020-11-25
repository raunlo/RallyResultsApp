package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyStagePersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.StageResultsPersistencePort;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorPairDbo_;
import com.ralohmus.rallyresults.persistence.entities.rally.RallyDbo_;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo_;
import com.ralohmus.rallyresults.persistence.entities.rally.StageResultDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageResultDbo_;
import com.ralohmus.rallyresults.persistence.mapper.StageResultDboToStageResultMapper;
import com.ralohmus.rallyresults.persistence.mapper.StageResultToStageResultDboMapper;
import com.ralohmus.rallyresults.persistence.repo.StageResultDboRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StageResultPersistenceAdapter extends BasePersistenceAdapter<StageResult, StageResultDbo> implements StageResultsPersistencePort {

    private final RallyStagePersistencePort rallyStagePersistencePort;

    StageResultPersistenceAdapter(StageResultDboRepository repository,
                                  StageResultDboToStageResultMapper dboToDomainMapper,
                                  StageResultToStageResultDboMapper domainToDboMapper,
                                  RallyStagePersistencePort rallyStagePersistencePort) {
        super(repository, dboToDomainMapper, domainToDboMapper);
        this.rallyStagePersistencePort = rallyStagePersistencePort;
    }

    @Override
    public StageResult save(StageResult stageResult) {
        var stage = rallyStagePersistencePort.findById(stageResult.getStage().getId()).orElseThrow();
        stageResult.setStage(stage);
        return super.save(stageResult);
    }

    @Override
    public boolean existsStageWithCompetitorId(Long id) {
        var jpaFilter = Specification.<StageResultDbo>where((root, query, cb) ->
                cb.equal(root.get(StageResultDbo_.COMPETITOR).get(CompetitorPairDbo_.ID), id));
        return !repository.findAll(jpaFilter).isEmpty();
    }

    @Override
    public Set<StageResult> findStageResultByCompetitorPairIdsAndStageIds(Set<Long> stageIds, Set<Long> competitorIds) {
        Specification<StageResultDbo> filter = Specification.where(((stageResultDbo, query, cb) ->
                stageResultDbo.get(StageResultDbo_.STAGE).get(StageDbo_.ID).in(stageIds)));

        if (competitorIds != null)
            filter = filter.and((stageResultDbo, query, cb) ->
                    stageResultDbo.get(StageResultDbo_.COMPETITOR).in(competitorIds));

        return repository.findAll(filter).stream().map(dboToDomainMapper::map).collect(Collectors.toSet());
    }

    @Override
    public Page<StageResult> getPage(Pageable pageable, Long stageId) {
        var filter = Specification.<StageResultDbo>where((stageResult, query, cb) ->
                cb.equal(stageResult.get(StageResultDbo_.STAGE).get(StageDbo_.ID), stageId));

        return repository.findAll(filter, pageable).map(dboToDomainMapper::map);
    }

    @Override
    public Optional<StageResult> findStageResult(StageResult stageResult) {
        var filter = Specification.<StageResultDbo>where((root, query, cb) ->
                cb.equal(root.get(StageResultDbo_.COMPETITOR).get(CompetitorPairDbo_.ID), stageResult.getCompetitor().getId()))
                .and((root, query, cb) ->
                        cb.equal(root.get(StageResultDbo_.STAGE).get(StageDbo_.ID), stageResult.getStage().getId()));

        return repository.findOne(filter).map(dboToDomainMapper::map);

    }

    @Override
    public List<StageResult> findAllStageResultsByStageId(Long stageId) {
        var filter = Specification.<StageResultDbo>where((root, query, cb) ->
                cb.equal(root.get(StageResultDbo_.STAGE).get(StageDbo_.ID), stageId));
        return repository.findAll(filter).stream().map(dboToDomainMapper::map).collect(Collectors.toList());
    }

    @Override
    public Optional<StageResult> findByCompetitorAndStage(Long competitorId, Long stageId) {
        var filter = Specification.<StageResultDbo>where((root, query, cb) ->
                cb.equal(root.get(StageResultDbo_.COMPETITOR).get(CompetitorPairDbo_.ID), competitorId))
                .and((root, query, cb) -> cb.equal(root.get(StageResultDbo_.STAGE).get(StageDbo_.ID),stageId));
        return repository.findOne(filter).map(dboToDomainMapper::map);
    }
}
