package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.service.ports.persistence.CompetitorPairPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.StageResultsPersistencePort;
import com.ralohmus.rallyresults.persistence.entities.competitor.*;
import com.ralohmus.rallyresults.persistence.exception.ResourceAlreadyAssociatedException;
import com.ralohmus.rallyresults.persistence.mapper.CompetitorPairDboToCompetitorPairMapper;
import com.ralohmus.rallyresults.persistence.mapper.CompetitorPairToCompetitorPairDboMapper;
import com.ralohmus.rallyresults.persistence.repo.CompetitorClassDboRepository;
import com.ralohmus.rallyresults.persistence.repo.CompetitorDboRepository;
import com.ralohmus.rallyresults.persistence.repo.CompetitorPairDboRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ralohmus.rallyresults.persistence.util.SpecificationUtils.LIKE_OPERATOR_TRANSFORMER;

@Component
public class CompetitorPairPersistenceAdapter extends BasePersistenceAdapter<CompetitorPair, CompetitorPairDbo> implements CompetitorPairPersistencePort {

    private final CompetitorDboRepository competitorDboRepository;
    private final StageResultsPersistencePort stageResultsPersistencePort;
    private final CompetitorClassDboRepository competitorClassDboRepository;

    CompetitorPairPersistenceAdapter(CompetitorPairDboRepository repository,
                                     CompetitorDboRepository competitorDboRepository,
                                     CompetitorPairDboToCompetitorPairMapper dboToDomainMapper,
                                     CompetitorPairToCompetitorPairDboMapper domainToDboMapper,
                                     StageResultsPersistencePort stageResultsPersistencePort,
                                     CompetitorClassDboRepository competitorClassDboRepository) {
        super(repository, dboToDomainMapper, domainToDboMapper);
        this.competitorDboRepository = competitorDboRepository;
        this.stageResultsPersistencePort = stageResultsPersistencePort;
        this.competitorClassDboRepository = competitorClassDboRepository;
    }

    @Override
    public void delete(Long id) {
        if (stageResultsPersistencePort.existsStageWithCompetitorId(id))
            throw new ResourceAlreadyAssociatedException();
        super.delete(id);
    }

    @Override
    public CompetitorPair save(CompetitorPair competitorPair) {
        var mappedDbo = domainToDboMapper.map(competitorPair);
        var updatedEntity = mappedDbo
                .setCoDriver(getCompetitor(mappedDbo.getCoDriver()))
                .setDriver(getCompetitor(mappedDbo.getDriver()))
                .setCompetitionClass(findCompetitorClass(mappedDbo.getCompetitionClass()));
        return dboToDomainMapper.map(repository.save(updatedEntity));
    }

    private CompetitorDbo getCompetitor(CompetitorDbo competitorDbo) {
        var jpaFilter = Specification
                .<CompetitorDbo>where((root, query, cb) -> cb.equal(cb.upper(root.get(CompetitorDbo_.FIRST_NAME)), competitorDbo.getFirstName().toUpperCase()))
                .and((root, query, cb) -> cb.equal(cb.upper(root.get(CompetitorDbo_.LAST_NAME)), competitorDbo.getLastName().toUpperCase()));
        var entityFromDatabase = competitorDboRepository.findOne(jpaFilter);
        return entityFromDatabase.orElseGet(() -> competitorDboRepository.save(competitorDbo));
    }

    @Override
    public List<CompetitorPair> findCompetitorPairByName(String name) {
        var jpaFilter = Specification
                .where(getSearchDriverByName(name))
                .or(getSearchCoDriverByName(name));
        return repository.findAll(jpaFilter).stream().map(dboToDomainMapper::map).collect(Collectors.toUnmodifiableList());
    }

    private Specification<CompetitorPairDbo> getSearchDriverByName(String name) {
        return Specification.<CompetitorPairDbo>where((root, query, cb) ->
                cb.like(cb.concat(
                        cb.upper(root.get(CompetitorPairDbo_.DRIVER).get(CompetitorDbo_.FIRST_NAME)),
                        cb.concat(" ",
                                cb.upper(root.get(CompetitorPairDbo_.DRIVER).get(CompetitorDbo_.LAST_NAME))
                        )
                ), LIKE_OPERATOR_TRANSFORMER.apply(name)));
    }

    private Specification<CompetitorPairDbo> getSearchCoDriverByName(String name) {
        return Specification.<CompetitorPairDbo>where((root, query, cb) ->
                cb.like(cb.concat(
                        cb.upper(root.get(CompetitorPairDbo_.CO_DRIVER).get(CompetitorDbo_.FIRST_NAME)),
                        cb.concat(" ",
                                cb.upper(root.get(CompetitorPairDbo_.CO_DRIVER).get(CompetitorDbo_.LAST_NAME))
                        )
                ), LIKE_OPERATOR_TRANSFORMER.apply(name)));
    }

    @Override
    public CompetitorPair findOrSaveCompetitorPair(CompetitorPair competitorPair) {
        var competitorPairDbo = domainToDboMapper.map(competitorPair);

        competitorPairDbo.setCoDriver(getCompetitor(competitorPairDbo.getCoDriver()))
                .setDriver(getCompetitor(competitorPairDbo.getDriver()))
                .setCompetitionClass(findCompetitorClass(competitorPairDbo.getCompetitionClass()));

        var filter = Objects.requireNonNull(Specification.<CompetitorPairDbo>where((root, query, cb) ->
                cb.equal(root.get(CompetitorPairDbo_.COMPETITION_CLASS).get(CompetitorClassDbo_.ID), competitorPairDbo.getCompetitionClass().getId()))
                .and((root, query, cb) -> cb.equal(root.get(CompetitorPairDbo_.CO_DRIVER).get(CompetitorDbo_.ID), competitorPairDbo.getCoDriver().getId())))
                .and((root, query, cb) -> cb.equal(root.get(CompetitorPairDbo_.DRIVER).get(CompetitorDbo_.ID), competitorPairDbo.getDriver().getId()));

        return dboToDomainMapper.map(repository.findOne(filter).orElseGet(() -> repository.save(competitorPairDbo)));
    }

    @Override
    public List<String> getAllCompetitorClasses(String name) {
        var filter = Specification.<CompetitorClassDbo>where((root, query, cb) ->
                cb.like(cb.upper(root.get(CompetitorClassDbo_.VALUE)), LIKE_OPERATOR_TRANSFORMER.apply(name)));
        return competitorClassDboRepository.findAll(filter).stream()
                .map(CompetitorClassDbo::getValue)
                .collect(Collectors.toList());
    }

    private CompetitorClassDbo findCompetitorClass(CompetitorClassDbo competitorClassDbo) {
        var filter = Specification.<CompetitorClassDbo>where((root, query, cb) ->
                cb.equal(cb.upper(root.get(CompetitorClassDbo_.VALUE)), competitorClassDbo.getValue().toUpperCase()));
        return competitorClassDboRepository.findOne(filter)
                .orElseGet(() -> competitorClassDboRepository.save(competitorClassDbo));
    }
}
