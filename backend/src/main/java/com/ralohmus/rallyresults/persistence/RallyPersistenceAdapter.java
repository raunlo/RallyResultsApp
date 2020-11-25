package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyPersistencePort;
import com.ralohmus.rallyresults.persistence.entities.rally.*;
import com.ralohmus.rallyresults.persistence.mapper.RallyDboToRallyMapper;
import com.ralohmus.rallyresults.persistence.mapper.RallyToRallyDboMapper;
import com.ralohmus.rallyresults.persistence.repo.RallyRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ralohmus.rallyresults.persistence.util.SpecificationUtils.LIKE_OPERATOR_TRANSFORMER;


@Component
public class RallyPersistenceAdapter extends BasePersistenceAdapter<Rally, RallyDbo> implements RallyPersistencePort {

    public RallyPersistenceAdapter(RallyRepository rallyRepository,
                                   RallyDboToRallyMapper rallyDboToRallyMapper,
                                   RallyToRallyDboMapper rallyToRallyDboMapper) {
        super(rallyRepository, rallyDboToRallyMapper, rallyToRallyDboMapper);
    }

    @Override
    public List<Rally> findByRallyName(String name) {
        var filter = Specification.<RallyDbo>where((rallyDbo, query, cb) ->
                cb.like(cb.upper(rallyDbo.get(RallyDbo_.NAME)), LIKE_OPERATOR_TRANSFORMER.apply(name)));
      return repository.findAll(filter).stream().map(dboToDomainMapper::map).collect(Collectors.toList());
    }

    @Override
    public Optional<Rally> findRally(Rally rally) {
        var filter = Objects.requireNonNull(Specification.<RallyDbo>where((rallyDbo, query, cb) ->
                cb.equal(rallyDbo.get(RallyDbo_.NAME), rally.getName()))
                .and((rallyDbo, query, cb) -> cb.equal(rallyDbo.get(RallyDbo_.START), rally.getStart())))
                .and((rallyDbo, query, cb) -> cb.equal(rallyDbo.get(RallyDbo_.END), rally.getEnd()));
        return repository.findOne(filter).map(dboToDomainMapper::map);
    }

    @Override
    public Optional<Rally> findRally(String rallyName, LocalDate rallyStartDate, LocalDate rallyEndDate) {
        var filer = Objects.requireNonNull(Specification.<RallyDbo>where((root, query, cb) -> cb.equal(root.get(RallyDbo_.NAME), rallyName))
                .and((root, query, cb) -> cb.equal(root.get(RallyDbo_.START), rallyStartDate)))
                .and((root, query, cb) -> cb.equal(root.get(RallyDbo_.END), rallyEndDate));
        return repository.findOne(filer).map(dboToDomainMapper::map);
    }
}
