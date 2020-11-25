package com.ralohmus.rallyresults.importer.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.importer.model.RallyImport;
import com.ralohmus.rallyresults.importer.model.StageImport;
import com.ralohmus.rallyresults.importer.model.StageResultsImport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompetitorImportToCompetitorMapper extends BaseMapper<RallyImport, Rally> {

    @Mapping(target = "start", source = "startDate")
    @Mapping(target = "rallyStages", source = "stages")
    @Mapping(target = "end", source = "endDate")
    @Mapping(target = "country", expression = "java(\"Estonia\")")
    @Mapping(target = "id", ignore = true)
    Rally map(RallyImport competitor);

    @Mapping(target = "trackName", source = "name")
    @Mapping(target = "stageResults", source = "stageResultsImport")
    @Mapping(target = "stageNumber", source = "stageNumber")
    @Mapping(target = "rally", ignore = true)
    @Mapping(target = "id", ignore = true)
    RallyStage mapRallyStage(StageImport stageImport);

    @Mapping(target = "interrupted", expression = "java(false)")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "competitor", source = "competitorPairImport")
    StageResult mapStageResult(StageResultsImport stageResultsImport);


}
