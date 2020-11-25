package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.competitor.Competitor;
import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.CompetitorDto;
import com.ralohmus.rallyresults.rest.admin.dto.CompetitorPairDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompetitorPairDtoToCompetitorPairMapper extends BaseMapper<CompetitorPairDto, CompetitorPair> {

    CompetitorPair map(CompetitorPairDto competitorPairDto);

    Competitor mapCompetitor(CompetitorDto competitorDto);
}
