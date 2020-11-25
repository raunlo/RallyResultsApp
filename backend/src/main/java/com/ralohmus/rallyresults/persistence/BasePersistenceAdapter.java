package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.core.service.ports.persistence.CrudPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.PagePersistencePort;
import com.ralohmus.rallyresults.persistence.repo.JpaRepositoryAndSpecificationWrapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Create, read, delete, update, get page methods for all database objects
 *
 * @param <TDomain> Domain object type
 * @param <TDbo>    Database object type
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BasePersistenceAdapter<TDomain, TDbo> implements CrudPersistencePort<TDomain>, PagePersistencePort<TDomain> {

    protected final JpaRepositoryAndSpecificationWrapper<TDbo> repository;
    protected final BaseMapper<TDbo, TDomain> dboToDomainMapper;
    protected final BaseMapper<TDomain, TDbo> domainToDboMapper;

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

    @Override
    public Page<TDomain> getPage(Pageable pageable) {
       return repository.findAll(pageable).map(dboToDomainMapper::map);
    }
}
