package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.rally.RallyDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CompetitorPairToCompetitorPairDboMapper.class})
public interface RallyToRallyDboMapper extends BaseMapper<Rally, RallyDbo> {

    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "stages", ignore = true)
    RallyDbo map(Rally rally);
}
