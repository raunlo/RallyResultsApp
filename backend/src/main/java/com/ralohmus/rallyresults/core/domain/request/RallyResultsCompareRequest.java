package com.ralohmus.rallyresults.core.domain.request;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class RallyResultsCompareRequest {
//    public enum RequestType {
//        STAGE, COMPETITOR
//    }

//    private RequestType requestType;
    private Set<Long> competitorIds;
    private Long stageId;
//    private Long rallyId;
}
