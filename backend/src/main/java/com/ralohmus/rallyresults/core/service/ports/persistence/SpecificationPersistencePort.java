package com.ralohmus.rallyresults.core.service.ports.persistence;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface SpecificationPersistencePort<TDomain, TDbo> {

    List<TDomain> findAll(Specification<TDbo> specification);

    Optional<TDomain> findOne(Specification<TDbo> specification);
}
