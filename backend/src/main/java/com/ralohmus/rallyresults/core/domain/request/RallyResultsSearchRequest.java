package com.ralohmus.rallyresults.core.domain.request;

import lombok.Data;

import java.util.function.Function;

@Data
public class RallyResultsSearchRequest {
    private final Function<String, String> fun = value -> "%" + (value == null ? "" : value.toUpperCase()) + "%";

    private String stageName;
    private String competitorName;
    private String rallyName;

//    private boolean isSearchByCompetitor() {
//        return competitorName != null && stageName == null && rallyName == null;
//    }
//
//    private boolean isSearchByCompetitorAndRally() {
//        return competitorName != null && rallyName != null && stageName == null;
//    }
//
//    private boolean isSearchByRally() {
//        return competitorName == null && rallyName != null && stageName == null;
//    }
//
//    private boolean isSearchByRallyAndStage() {
//        return competitorName == null && rallyName != null && stageName != null;
//    }
//
//    private boolean isSearchByRallyAndStageAndCompetitor() {
//        return competitorName != null && stageName != null && rallyName != null;
//    }
//
//    private boolean isSearchByCompetitorAndStage() {
//        return rallyName == null && stageName != null && competitorName != null;
//    }

}
