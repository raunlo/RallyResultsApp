package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.competitor.Competitor;
import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.CompetitorDto;
import com.ralohmus.rallyresults.rest.admin.dto.CompetitorPairDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface CompetitorPairToCompetitorPairDtoMapper extends BaseMapper<CompetitorPair, CompetitorPairDto> {

    CompetitorPairDto map(CompetitorPair competitorPair);

    CompetitorDto mapCompetitor(Competitor competitor);
}
