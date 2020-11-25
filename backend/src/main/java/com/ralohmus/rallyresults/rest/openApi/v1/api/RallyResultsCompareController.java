package com.ralohmus.rallyresults.rest.openApi.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.FindRallyResultsCompareDataPort;
import com.ralohmus.rallyresults.rest.openApi.dto.CompetitorAverageSpeedDto;
import com.ralohmus.rallyresults.rest.openApi.mapper.RallyAverageSpeedToRallyAverageSpeedDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/compare")
@RequiredArgsConstructor
public class RallyResultsCompareController {

    private final FindRallyResultsCompareDataPort findRallyResultsCompareDataPort;

    private final RallyAverageSpeedToRallyAverageSpeedDtoMapper rallyAverageSpeedToRallyAverageSpeedDtoMapper;

    @GetMapping("/average-speed/{stageId}")
    public ResponseEntity<List<CompetitorAverageSpeedDto>> searchForRallyResults(@PathVariable Long stageId) {
        return ResponseEntity.ok(findRallyResultsCompareDataPort.findRallyAverageSpeedCompareData(stageId)
                .stream().map(rallyAverageSpeedToRallyAverageSpeedDtoMapper::map)
                .collect(Collectors.toList()));
    }
}
