package com.ralohmus.rallyresults.rest.admin.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.CrudRallyStagePort;
import com.ralohmus.rallyresults.rest.admin.dto.RallyStageDto;
import com.ralohmus.rallyresults.rest.admin.mapper.RallyStageDtoToRallyStageMapper;
import com.ralohmus.rallyresults.rest.admin.mapper.RallyStageToRallyStageDtoMapper;
import com.ralohmus.rallyresults.rest.util.CurrentURI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/rally/{rallyId}/stage")
@RequiredArgsConstructor
public class RallyStageCrudController implements CurrentURI {

    private final CrudRallyStagePort crudRallyStagePort;
    private final RallyStageToRallyStageDtoMapper rallyStageToRallyStageDtoMapper;
    private final RallyStageDtoToRallyStageMapper rallyStageDtoToRallyStageMapper;

    @GetMapping
    public ResponseEntity<Page<RallyStageDto>> getRallyStagesPage(@PageableDefault(sort = {"stageNumber"}, direction = Sort.Direction.ASC) Pageable pageable,
                                                                  @PathVariable Long rallyId) {
        return ResponseEntity.ok(
                crudRallyStagePort.getPage(pageable, rallyId).map(rallyStageToRallyStageDtoMapper::map)
        );
    }

    @PutMapping
    public ResponseEntity<RallyStageDto> updateRallyStage(@RequestBody RallyStageDto rallyStageDto, @PathVariable Long rallyId) {
        return ResponseEntity.ok(
                rallyStageToRallyStageDtoMapper.map(
                        crudRallyStagePort.save(rallyStageDtoToRallyStageMapper.map(rallyStageDto))
                )
        );
    }

    @PostMapping
    public ResponseEntity<RallyStageDto> postRallyStage(@RequestBody RallyStageDto rallyStageDto, @PathVariable Long rallyId) {

        var savedStage = rallyStageToRallyStageDtoMapper.map(
                crudRallyStagePort.save(rallyStageDtoToRallyStageMapper.map(rallyStageDto)));
        var getLink = getCurrentUriWithId().buildAndExpand(rallyStageDto.getId()).toUri();
        return ResponseEntity.created(getLink).body(savedStage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RallyStageDto> getRallyStage(@PathVariable Long id, @PathVariable Long rallyId) {
        return ResponseEntity.of(crudRallyStagePort.findById(id).map(rallyStageToRallyStageDtoMapper::map));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRallyStage(@PathVariable Long id, @PathVariable Long rallyId) {
        crudRallyStagePort.delete(id);
        return ResponseEntity.noContent().build();
    }
}
