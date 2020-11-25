package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.RallyStageDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RallyStageDtoToRallyStageMapper extends BaseMapper<RallyStageDto, RallyStage> {

    @Mapping(target = "rally", expression = "java(createEmptyRallyWithId(rallyStageDto.getRallyId()))" )
    @Mapping(target = "stageResults", ignore = true)
    RallyStage map(RallyStageDto rallyStageDto);

    default Rally createEmptyRallyWithId(Long id) {
        return new Rally().setId(id);
    }
}
