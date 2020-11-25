package com.ralohmus.rallyresults.rest.openApi.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.FindRallyResultsCompareDataPort;
import com.ralohmus.rallyresults.core.service.ports.application.FindRallyResultsPort;
import com.ralohmus.rallyresults.core.service.ports.application.SearchRalliesPort;
import com.ralohmus.rallyresults.rest.admin.dto.RallyDto;
import com.ralohmus.rallyresults.rest.admin.mapper.RallyToRallyDtoMapper;
import com.ralohmus.rallyresults.rest.openApi.dto.RallyResultsSearchRequestDto;
import com.ralohmus.rallyresults.rest.openApi.mapper.RallySearchRequestDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {

    private final RallySearchRequestDtoMapper rallySearchRequestDtoMapper;
    private final RallyToRallyDtoMapper rallyToRallyDtoMapper;


    private final FindRallyResultsPort findRallyResultsPort;
    private final FindRallyResultsCompareDataPort findRallyResultsCompareDataPort;
    private final SearchRalliesPort searchRalliesPort;

    @GetMapping("rally-results")
    public ResponseEntity<Page<RallyDto>> searchForRallyResults(@PageableDefault(sort = {"start" }, direction = Sort.Direction.DESC) Pageable pageable,
                                                                      RallyResultsSearchRequestDto rallySearchRequestDto) {
        return ResponseEntity.ok(findRallyResultsPort
                .findRallyResults(rallySearchRequestDtoMapper.map(rallySearchRequestDto), pageable)
                .map(rallyToRallyDtoMapper::map));
    }

    @GetMapping("rallies/search-by-name/{rallyName}")
    public ResponseEntity<List<RallyDto>> searchForRallies(@PathVariable String rallyName) {
        return ResponseEntity.ok(searchRalliesPort.searchRallies(rallyName).stream()
                .map(rallyToRallyDtoMapper::map).collect(Collectors.toList()));
    }

//    @GetMapping("/compare/competitiors/{stageId}")
//    public List<RallyResultsCompareData> getRallyResultsCompareData(
//            RallyResultsCompareRequestDto request) {
//       return findRallyResultsCompareDataPort.findRallyResultsCompareData(rallyResultsCompareRequestDtoToRallyResultsCompareRequestMapper.map(request));
////        var comparableData = findRallyResultsCompareDataPort.findRallyResultsCompareData(
////                rallyResultsCompareRequestDtoToRallyResultsCompareRequestMapper.map(request)
////        );
////
////        return ResponseEntity.ok(
////            rallyResultsCompareDataToRallyResultsCompareDataDtoMapper.map(comparableData)
////        );
//    }
}