package com.ralohmus.rallyresults.persistence.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Jpa repository interface wrapper with jpa repository and jpa specifications methods
 * @param <TDbo> Database object type
 */
@NoRepositoryBean
public interface JpaRepositoryAndSpecificationWrapper<TDbo> extends JpaRepository<TDbo, Long>, JpaSpecificationExecutor<TDbo>, PagingAndSortingRepository<TDbo, Long> {
}
