package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.ports.persistence.CrudPersistencePort;
import com.ralohmus.rallyresults.core.ports.persistence.SpecificationPersistencePort;
import com.ralohmus.rallyresults.persistence.mapper.base.DboToDomainBaseMapper;
import com.ralohmus.rallyresults.persistence.mapper.base.DomainToDboBaseMapper;
import com.ralohmus.rallyresults.persistence.repos.JpaRepositoryAndSpecificationWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Create, read, delete, update methods for all database objects
 *
 * @param <TDomain> Domain object type
 * @param <TDbo>    Database object type
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class CrudPersistenceAdapter<TDomain, TDbo> implements CrudPersistencePort<TDomain> {

    protected final JpaRepositoryAndSpecificationWrapper<TDbo> repository;
    protected final DboToDomainBaseMapper<TDomain, TDbo> dboToDomainMapper;
    protected final DomainToDboBaseMapper<TDomain, TDbo> domainToDboMapper;

    @Override
    public Optional<TDomain> findById(Long id) {
        return repository.findById(id).map(dboToDomainMapper::map);
    }

    @Override
    public List<TDomain> findAll() {
        return repository.findAll().stream().map(dboToDomainMapper::map).collect(Collectors.toList());
    }

    @Override
    public TDomain save(TDomain tDomain) {
        var dbo = domainToDboMapper.map(tDomain);
        return dboToDomainMapper.map(repository.save(dbo));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
