package com.ralohmus.rallyresults.persistence.mapper.base;

public interface DomainToDboBaseMapper<TDomain, TDbo> {

    TDbo map(TDomain tDomain);
}
