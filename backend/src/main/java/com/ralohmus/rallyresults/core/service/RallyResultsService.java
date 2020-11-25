package com.ralohmus.rallyresults.core.service;

import com.ralohmus.rallyresults.core.domain.CompetitorAverageSpeed;
import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import com.ralohmus.rallyresults.core.domain.request.RallyResultsSearchRequest;
import com.ralohmus.rallyresults.core.service.ports.application.FindCompetitorsByNamePort;
import com.ralohmus.rallyresults.core.service.ports.application.FindRallyResultsCompareDataPort;
import com.ralohmus.rallyresults.core.service.ports.application.FindRallyResultsPort;
import com.ralohmus.rallyresults.core.service.ports.persistence.RallyPersistencePort;
import com.ralohmus.rallyresults.core.service.ports.persistence.StageResultsPersistencePort;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Component
@RequiredArgsConstructor
public class RallyResultsService implements FindRallyResultsPort, FindRallyResultsCompareDataPort {

    private final static String STAGE_NAME = "SS%d %s";

    private final FindCompetitorsByNamePort findCompetitorsByNamePort;
    private final RallyPersistencePort rallyPersistencePort;
    private final StageResultsPersistencePort stageResultsPersistencePort;

    @Override
    public Page<Rally> findRallyResults(RallyResultsSearchRequest request, Pageable pageable) {
        var searchCompetitors = StringUtils.isNotBlank(request.getCompetitorName())
                ? findCompetitorsByNamePort.getCompetitorPair(request.getCompetitorName()) : null;

        var rallyList = rallyPersistencePort.findByRallyName(request.getRallyName());
        Set<RallyStage> stageList = getExtractedRallyStages(request, rallyList);

        var stageIds = stageList.stream().map(RallyStage::getId).collect(Collectors.toUnmodifiableSet());
        if (CollectionUtils.isEmpty(stageIds)) return  Page.empty(pageable);


        var stageResults = stageResultsPersistencePort.findStageResultByCompetitorPairIdsAndStageIds(stageIds,
                searchCompetitors == null ? null : searchCompetitors.stream().map(CompetitorPair::getId).collect(Collectors.toUnmodifiableSet()));

        stageList = updateStages(stageList, stageResults);
        return updateRallies(rallyList, stageList, pageable);
    }

    private Set<RallyStage> getExtractedRallyStages(RallyResultsSearchRequest request, List<Rally> rallyList) {
        Set<RallyStage> stageList;
        var stageListStream = rallyList.stream().map(rally ->
                rally.getRallyStages().stream().map(stage -> stage.setRally(new Rally().setId(rally.getId()))).collect(Collectors.toList()))
                .flatMap(Collection::stream);


        if (StringUtils.isNotBlank(request.getStageName())) {
            stageList = stageListStream
                    .filter(stage -> stage.getTrackName().toUpperCase().contains(request.getStageName().toUpperCase()))
                    .collect(Collectors.toSet());
        } else {
            stageList = stageListStream.collect(Collectors.toSet());
        }
        return stageList;
    }

    private Page<Rally> updateRallies(List<Rally> rallyList, Set<RallyStage> stageList, Pageable pageable) {
        var pageStart = pageable.getPageNumber() * pageable.getPageSize();
        var pageEnd = pageStart + pageable.getPageSize();
        rallyList = rallyList.stream().peek(rally -> {
            var foundStages = stageList.stream().sorted(Comparator.comparing(RallyStage::getStageNumber))
                    .filter(rallyStage -> rallyStage.getRally().getId().equals(rally.getId()))
                    .collect(Collectors.toUnmodifiableList());
            rally.setRallyStages(foundStages);
        })
        .collect(Collectors.toList());

        var filteredRallies = rallyList.stream()
                .filter(r -> !CollectionUtils.isEmpty(r.getRallyStages()))
                .collect(Collectors.toUnmodifiableList());
        return new PageImpl<>(sliceContent(filteredRallies, pageStart, pageEnd), pageable, filteredRallies.size());
    }

    private <T> List<T> sliceContent(List<T> content, Integer start, Integer end) {
       return content.stream()
                .skip(start)
                .limit(end - start)
        .collect(Collectors.toList());
    }

    private Set<RallyStage> updateStages(Set<RallyStage> stageList, Set<StageResult> stageResults) {
        stageList.forEach(stage -> {
            var foundResults = stageResults.stream().filter(result -> result.getStage().getId().equals(stage.getId()))
                    .sorted()
                    .collect(Collectors.toUnmodifiableList());
            stage.setStageResults(foundResults);
        });
        return stageList.stream()
                .filter(stage -> !CollectionUtils.isEmpty(stage.getStageResults()))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public List<CompetitorAverageSpeed> findRallyAverageSpeedCompareData(Long stageId) {
        var stageResults = stageResultsPersistencePort.findAllStageResultsByStageId(stageId);
        return stageResults.stream().map(this::createComparableData).
                collect(Collectors.toList());

    }

    private CompetitorAverageSpeed createComparableData(StageResult stageResults) {
        var timeSplit = stageResults.getTime().split(":");
        var timeInHours = Double.parseDouble(timeSplit[0]) / 60 + Double.parseDouble(timeSplit[1]) / Math.pow(60, 2);
        return new CompetitorAverageSpeed()
                .setAverageSpeed(stageResults.getStage().getLength().divide(BigDecimal.valueOf(timeInHours), RoundingMode.HALF_UP))
                .setCompetitorName(stageResults.getCompetitor().getCompetitorPairFullNames());
    }
}
