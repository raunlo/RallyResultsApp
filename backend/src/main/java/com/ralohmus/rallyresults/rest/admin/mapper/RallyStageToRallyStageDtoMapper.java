package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.RallyStageDto;
import com.ralohmus.rallyresults.rest.admin.dto.StageResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RallyStageToRallyStageDtoMapper extends BaseMapper<RallyStage, RallyStageDto> {

    @Mapping(target = "rallyId", source = "rally.id")
    RallyStageDto map(RallyStage rallyStage);

    @Mapping(target = "rallyStageId", ignore = true)
    StageResultDto mapStageResult(StageResult stageResult);
}
