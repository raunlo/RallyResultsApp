package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.RallyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RallyDtoToRallyMapper extends BaseMapper<RallyDto, Rally> {

    @Mapping(target = "start", source = "startDate")
    @Mapping(target = "end", source = "endDate")
    @Mapping(target = "country", source = "country")
    @Mapping(target = "name", source = "name")
    @Mapping(target ="rallyStages", ignore = true)
    Rally map(RallyDto rallyDto);
}
