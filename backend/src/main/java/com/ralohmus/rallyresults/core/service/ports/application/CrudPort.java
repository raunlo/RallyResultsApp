package com.ralohmus.rallyresults.core.service.ports.application;

import java.util.List;
import java.util.Optional;

public interface CrudPort<TDomain> {
    TDomain save(TDomain domain);

    Optional<TDomain> findById(Long id);

    List<TDomain> findAll();

    void delete(Long id);
}
