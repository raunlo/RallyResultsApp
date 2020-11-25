package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.service.ports.application.CrudPort;
import com.ralohmus.rallyresults.core.service.ports.application.PagePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.CrudPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.PagePersistencePort;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BaseService<TDomain, PersistencePort extends CrudPersistencePort<TDomain> & PagePersistencePort<TDomain>> implements CrudPort<TDomain>, PagePort<TDomain> {

    protected final PersistencePort persistencePort;

    @Override
    public TDomain save(TDomain tDomain) {
        return persistencePort.save(tDomain);
    }

    @Override
    public Optional<TDomain> findById(Long id) {
        return persistencePort.findById(id);
    }

    @Override
    public List<TDomain> findAll() {
        return persistencePort.findAll();
    }


    @Override
    public void delete(Long id) {
        persistencePort.delete(id);
    }


    @Override
    public Page<TDomain> getPage(Pageable pageable) {
        return persistencePort.getPage(pageable);
    }
}
