package com.ralohmus.rallyresults.rest.openApi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RallyResultsCompareRequestDto {
   public  enum RequestTypeDto {
        STAGE, COMPETITOR
    }

    private RequestTypeDto type;
    private List<String> competitorNames;
    private String stageName;
    private Long rallyId;
    private Long stageId;
}
