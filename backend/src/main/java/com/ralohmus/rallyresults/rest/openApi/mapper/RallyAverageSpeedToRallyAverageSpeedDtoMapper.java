package com.ralohmus.rallyresults.rest.openApi.mapper;

import com.ralohmus.rallyresults.core.domain.CompetitorAverageSpeed;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.openApi.dto.CompetitorAverageSpeedDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RallyAverageSpeedToRallyAverageSpeedDtoMapper extends BaseMapper<CompetitorAverageSpeed, CompetitorAverageSpeedDto> {


    CompetitorAverageSpeedDto map(CompetitorAverageSpeed competitorAverageSpeed);
}
