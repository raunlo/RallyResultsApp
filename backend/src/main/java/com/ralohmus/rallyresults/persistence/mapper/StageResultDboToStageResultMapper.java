package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageResultDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = CompetitorPairDboToCompetitorPairMapper.class)
public interface StageResultDboToStageResultMapper extends BaseMapper<StageResultDbo, StageResult> {

    @Mapping(target = "time", source = "resultTime")
    StageResult map(StageResultDbo stageResultDbo);

    @Mapping(target = "stageResults", ignore = true)
    @Mapping(target = "rally", ignore = true)
    RallyStage mapStage(StageDbo stageDbo);
}
