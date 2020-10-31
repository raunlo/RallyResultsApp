package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.Competitor;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorDbo;
import com.ralohmus.rallyresults.persistence.mapper.base.DomainToDboBaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitorToCompetitorDboMapper extends DomainToDboBaseMapper<Competitor, CompetitorDbo> {

    CompetitorDbo map(Competitor competitor);
}
