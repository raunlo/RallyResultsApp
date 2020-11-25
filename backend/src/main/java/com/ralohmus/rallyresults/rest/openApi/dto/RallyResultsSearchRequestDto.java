package com.ralohmus.rallyresults.rest.openApi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RallyResultsSearchRequestDto {
    private String rallyName;
    private String stageName;
    private String competitorName;
}
