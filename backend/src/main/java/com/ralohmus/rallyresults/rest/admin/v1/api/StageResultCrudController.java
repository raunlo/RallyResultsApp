package com.ralohmus.rallyresults.rest.admin.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.CrudStageResultsPort;
import com.ralohmus.rallyresults.rest.admin.dto.StageResultDto;
import com.ralohmus.rallyresults.rest.admin.mapper.StageResultDtoMapper;
import com.ralohmus.rallyresults.rest.util.CurrentURI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/{rallyId}/stage/{stageId}/results")
@RestController
public class StageResultCrudController implements CurrentURI {

    private final CrudStageResultsPort crudStageResultsPort;

    private final StageResultDtoMapper stageResultDtoMapper;

    @PostMapping
    public ResponseEntity<StageResultDto> saveStageResults(@RequestBody StageResultDto stageResultDto) {
        var savedentity = crudStageResultsPort.save(stageResultDtoMapper.map(stageResultDto));
        var selfLink = getCurrentUriWithId().build(savedentity.getId());
        return ResponseEntity.created(selfLink).body(stageResultDtoMapper.map(savedentity));

    }

    @GetMapping
    public ResponseEntity<Page<StageResultDto>> getRallyStagesPage(@PageableDefault Pageable pageable, @PathVariable Long stageId) {
        return ResponseEntity.ok(
                crudStageResultsPort.getPage(pageable, stageId).map(stageResultDtoMapper::map)
        );
    }

    @PutMapping
    public ResponseEntity<StageResultDto> updateRallyStage(@RequestBody StageResultDto stageResultDto) {
        return ResponseEntity.ok(
                stageResultDtoMapper.map(
                        crudStageResultsPort.save(stageResultDtoMapper.map(stageResultDto))
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRally(@PathVariable Long id) {
        crudStageResultsPort.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StageResultDto> getRallyById(@PathVariable Long id) {
        return ResponseEntity.of(crudStageResultsPort.findById(id).map(stageResultDtoMapper::map));
    }
}
