package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.CompetitorAverageSpeed;

import java.util.List;

public interface FindRallyResultsCompareDataPort {

    List<CompetitorAverageSpeed> findRallyAverageSpeedCompareData(Long stageId);
}
