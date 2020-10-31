package com.ralohmus.rallyresults.persistence.mapper.base;

/**
 * Base mapper for all database object to domain objects mappers
 * @param <TDomain>
 * @param <TDbo>
 */
public interface DboToDomainBaseMapper<TDomain, TDbo> {

    TDomain map(TDbo tDbo);
}
