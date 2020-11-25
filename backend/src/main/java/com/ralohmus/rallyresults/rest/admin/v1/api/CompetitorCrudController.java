package com.ralohmus.rallyresults.rest.admin.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.CrudCompetitorPairPort;
import com.ralohmus.rallyresults.core.service.ports.application.FindCompetitorsByNamePort;
import com.ralohmus.rallyresults.core.service.ports.application.FindCompetitorClassPort;
import com.ralohmus.rallyresults.rest.admin.dto.CompetitorPairDto;
import com.ralohmus.rallyresults.rest.admin.mapper.CompetitorPairDtoToCompetitorPairMapper;
import com.ralohmus.rallyresults.rest.admin.mapper.CompetitorPairToCompetitorPairDtoMapper;
import com.ralohmus.rallyresults.rest.util.CurrentURI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/admin/competitor-pair")
public class CompetitorCrudController implements CurrentURI {

    private final CrudCompetitorPairPort crudCompetitorPairPort;
    private final FindCompetitorsByNamePort findCompetitorsByNamePort;
    private final FindCompetitorClassPort findCompetitorClassPort;

    private final CompetitorPairDtoToCompetitorPairMapper competitorPairDtoToCompetitorPairMapper;
    private final CompetitorPairToCompetitorPairDtoMapper competitorPairToCompetitorPairDtoMapper;

    @PostMapping
    public ResponseEntity<CompetitorPairDto> createCompetitorPair(@RequestBody CompetitorPairDto competitorPairDto) {
        var savedResource = crudCompetitorPairPort.save(competitorPairDtoToCompetitorPairMapper.map(competitorPairDto));

        var getUri = getCurrentUriWithId().buildAndExpand(competitorPairDto.getId()).toUri();
        return ResponseEntity.created(getUri).body(competitorPairToCompetitorPairDtoMapper.map(savedResource));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompetitorPair(@PathVariable Long id) {
        crudCompetitorPairPort.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<CompetitorPairDto>> getCompetitorPage(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(crudCompetitorPairPort.getPage(pageable).map(competitorPairToCompetitorPairDtoMapper::map));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompetitorPairDto> getRallyById(@PathVariable Long id) {
        return ResponseEntity.of((crudCompetitorPairPort.findById(id).map(competitorPairToCompetitorPairDtoMapper::map)));
    }

    @GetMapping("/find-by-name")
    public ResponseEntity<List<CompetitorPairDto>> findCompetitorByName(@RequestParam(name = "name" , defaultValue = "") String name) {
        return ResponseEntity.ok(
                findCompetitorsByNamePort.getCompetitorPair(name).stream()
                        .map(competitorPairToCompetitorPairDtoMapper::map)
                        .collect(Collectors.toUnmodifiableList())
        );
    }

    @GetMapping("/class/{name}")
    public ResponseEntity<List<String>> findAllCompetitorClasses(@PathVariable String name) {
        return ResponseEntity.ok(findCompetitorClassPort.getAllCompetitorClasses(name));
    }
}
