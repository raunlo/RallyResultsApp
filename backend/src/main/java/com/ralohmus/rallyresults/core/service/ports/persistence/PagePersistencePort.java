package com.ralohmus.rallyresults.core.service.ports.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagePersistencePort<TDomain> {
    Page<TDomain> getPage(Pageable pageable);
}
