package com.ralohmus.rallyresults.persistence.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Jpa repository interface wrapper with jpa repository and jpa specifications methods
 * @param <TDbo> Database object type
 */
@NoRepositoryBean
public interface JpaRepositoryAndSpecificationWrapper<TDbo> extends JpaRepository<TDbo, Long>, JpaSpecificationExecutor<TDbo> {
}
