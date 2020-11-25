package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.StageDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RallyToRallyDboMapper.class, CompetitorPairToCompetitorPairDboMapper.class, StageResultToStageResultDboMapper.class})
public interface RallyStageToStageDboMapper extends BaseMapper<RallyStage, StageDbo> {

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    StageDbo map(RallyStage rallyStage);
}
