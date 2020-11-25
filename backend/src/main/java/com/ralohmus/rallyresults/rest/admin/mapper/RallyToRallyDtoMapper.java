package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.RallyDto;
import com.ralohmus.rallyresults.rest.admin.dto.RallyStageDto;
import com.ralohmus.rallyresults.rest.admin.dto.StageResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RallyToRallyDtoMapper extends BaseMapper<Rally, RallyDto> {

    @Mapping(target = "startDate", source = "start")
    @Mapping(target = "endDate", source = "end")
    RallyDto map(Rally rally);


    @Mapping(target = "endDate", source = "end")
    @Mapping(target = "startDate", source = "start")
    RallyDto mapResults(Rally rally);


    @Mapping(target = "rallyId", ignore = true)
    RallyStageDto mapStage(RallyStage rallyStage);


    @Mapping(target = "rallyStageId", ignore = true)
    StageResultDto mapStageResult(StageResult stageResult);
}
