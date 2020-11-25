package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.rally.Rally;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface RallyPersistencePort extends CrudPersistencePort<Rally>, PagePersistencePort<Rally> {

    List<Rally> findByRallyName(String name);

    Optional<Rally> findRally(Rally rally);

    Optional<Rally> findRally(String rallyName, LocalDate rallyStartDate, LocalDate rallyEndDate);

}
