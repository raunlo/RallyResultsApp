package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.RallyDbo;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StageDboToRallyStageMapper extends BaseMapper<StageDbo, RallyStage> {
    @Mapping(target = "stageResults", ignore = true)
    RallyStage map(StageDbo stageDbo);


    @Mapping(target = "rallyStages", ignore = true)
    Rally mapRally(RallyDbo rallyDbo);
}
