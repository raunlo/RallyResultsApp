package com.ralohmus.rallyresults.core.service.ports.persistence;

import java.util.List;
import java.util.Optional;

/**
 *  Crud service port
 * @param <TDomain> Type of domain object
 */
public interface CrudPersistencePort<TDomain> {

    Optional<TDomain> findById(Long id);

    List<TDomain> findAll();

    TDomain save(TDomain tDomain);

    void delete(Long id);
}
