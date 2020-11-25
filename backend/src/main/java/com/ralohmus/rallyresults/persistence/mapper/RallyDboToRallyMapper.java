package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.RallyDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageResultDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompetitorPairDboToCompetitorPairMapper.class})
public interface RallyDboToRallyMapper extends BaseMapper<RallyDbo, Rally> {

    @Mapping(target = "rallyStages", source = "stages")
    Rally map(RallyDbo rally);

    @Mapping(target = "rally", ignore = true)
    RallyStage map(StageDbo stageDbo);

    @Mapping(target = "time", source = "resultTime")
    @Mapping(target = "stage", ignore = true)
    StageResult map(StageResultDbo stageResultDbo);
}
