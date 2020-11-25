package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.competitor.Competitor;
import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorDbo;

import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorPairDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetitorPairDboToCompetitorPairMapper extends BaseMapper<CompetitorPairDbo, CompetitorPair> {
    @Mapping(target = "competitionClass", source = "competitionClass.value")
    CompetitorPair map(CompetitorPairDbo dbo);

    Competitor mapCompetitor(CompetitorDbo competitorDbo);
}
