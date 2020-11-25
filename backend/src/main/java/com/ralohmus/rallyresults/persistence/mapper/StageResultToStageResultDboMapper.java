package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.StageResultDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompetitorPairToCompetitorPairDboMapper.class, RallyStageToStageDboMapper.class,
        RallyToRallyDboMapper.class})
public interface StageResultToStageResultDboMapper extends BaseMapper<StageResult, StageResultDbo> {

    @Mapping(target = "resultTime", source = "time")
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    StageResultDbo map(StageResult stageResult);
}
