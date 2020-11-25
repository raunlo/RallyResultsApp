package com.ralohmus.rallyresults.rest.openApi.mapper;

import com.ralohmus.rallyresults.core.domain.request.RallyResultsSearchRequest;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.openApi.dto.RallyResultsSearchRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RallySearchRequestDtoMapper extends BaseMapper<RallyResultsSearchRequestDto, RallyResultsSearchRequest> {

    RallyResultsSearchRequest map(RallyResultsSearchRequestDto rallyResultsSearchRequestDto);
}
