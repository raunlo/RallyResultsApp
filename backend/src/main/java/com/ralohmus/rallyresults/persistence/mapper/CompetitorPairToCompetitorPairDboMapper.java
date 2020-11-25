package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.competitor.Competitor;
import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorClassDbo;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorDbo;
import com.ralohmus.rallyresults.persistence.entities.competitor.CompetitorPairDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompetitorPairToCompetitorPairDboMapper extends BaseMapper<CompetitorPair, CompetitorPairDbo> {

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "competitionClass", expression = "java(create(competitorPair.getCompetitionClass()))")
    CompetitorPairDbo map(CompetitorPair competitorPair);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    CompetitorDbo mapCompetitor(Competitor competitor);

    default CompetitorClassDbo create(String value) {
       return new CompetitorClassDbo().setValue(value);
    }
}
