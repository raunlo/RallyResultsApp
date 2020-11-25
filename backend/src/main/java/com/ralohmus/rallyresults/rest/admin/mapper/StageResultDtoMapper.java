package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.StageResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StageResultDtoMapper extends BaseMapper<StageResultDto, StageResult> {

    @Mapping(target = "stage", expression = "java(createRallyStage(stageResultDto.getRallyStageId()))")
    StageResult map(StageResultDto stageResultDto);

    default RallyStage createRallyStage(Long id) {
        return new RallyStage().setId(id);
    }

    @Mapping(target = "rallyStageId", ignore = true)
    StageResultDto map(StageResult stageResultDto);

}
