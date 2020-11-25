package com.ralohmus.rallyresults.rest.admin.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.CrudRallyPort;
import com.ralohmus.rallyresults.rest.admin.dto.RallyDto;
import com.ralohmus.rallyresults.rest.admin.mapper.RallyDtoToRallyMapper;
import com.ralohmus.rallyresults.rest.admin.mapper.RallyToRallyDtoMapper;
import com.ralohmus.rallyresults.rest.util.CurrentURI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/admin/rally")
@RequiredArgsConstructor
public class RallyCrudController implements CurrentURI {

    private final RallyDtoToRallyMapper rallyDtoToRallyMapper;
    private final RallyToRallyDtoMapper rallyToRallyDtoMapper;
    private final CrudRallyPort crudRallyPort;

    @PostMapping
    public ResponseEntity<RallyDto> createRally(@RequestBody RallyDto rallyDto) {

        var savedRally = rallyToRallyDtoMapper.map(crudRallyPort.save(rallyDtoToRallyMapper.map(rallyDto)));

        var location = getCurrentUriWithId().buildAndExpand(savedRally.getId()).toUri();
        return ResponseEntity.created(location).body(savedRally);
    }

    @PutMapping
    public ResponseEntity<RallyDto> updateRally(@RequestBody RallyDto rallyDto) {
        return  ResponseEntity.ok(
                rallyToRallyDtoMapper.map(
                        crudRallyPort.save(rallyDtoToRallyMapper.map(rallyDto))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRally(@PathVariable Long id) {
        crudRallyPort.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<RallyDto>> getRallyPage(@PageableDefault(sort =  {"start"}, direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok(
                crudRallyPort.getPage(pageable).map(rallyToRallyDtoMapper::map)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RallyDto> getRallyById(@PathVariable Long id) {
        return ResponseEntity.of(crudRallyPort.findById(id).map(rallyToRallyDtoMapper::map));
    }
}
