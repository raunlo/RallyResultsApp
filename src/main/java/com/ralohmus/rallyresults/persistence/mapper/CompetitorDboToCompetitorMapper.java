package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.Competitor;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorDbo;
import com.ralohmus.rallyresults.persistence.mapper.base.DboToDomainBaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitorDboToCompetitorMapper extends DboToDomainBaseMapper<Competitor, CompetitorDbo> {

    Competitor map(CompetitorDbo dbo);
}
