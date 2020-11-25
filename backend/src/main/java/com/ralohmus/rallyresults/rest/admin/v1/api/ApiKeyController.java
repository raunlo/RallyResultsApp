package com.ralohmus.rallyresults.rest.admin.v1.api;

import com.ralohmus.rallyresults.core.service.ports.application.ApiKeyPort;
import com.ralohmus.rallyresults.rest.admin.dto.ApiKeyDto;
import com.ralohmus.rallyresults.rest.admin.mapper.ApiKeyDtoToApiKeyMapper;
import com.ralohmus.rallyresults.rest.admin.mapper.ApiKeyToApiKeyDtoMapper;
import com.ralohmus.rallyresults.rest.util.CurrentURI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/admin/api-key")
@RequiredArgsConstructor
public class ApiKeyController implements CurrentURI {

    private final ApiKeyPort apiKeyPort;
    private final ApiKeyDtoToApiKeyMapper apiKeyDtoToApiKeyMapper;
    private final ApiKeyToApiKeyDtoMapper apiKeyToApiKeyDtoMapper;

    @PostMapping
    public ResponseEntity<ApiKeyDto> saveApiKey(@RequestBody ApiKeyDto apiKeyDto) {
        var resource = apiKeyToApiKeyDtoMapper.map(
                apiKeyPort.save(apiKeyDtoToApiKeyMapper.map(apiKeyDto)));
        return ResponseEntity.created(getCurrentUriWithId().buildAndExpand(resource.getId()).toUri())
                .body(resource);
    }

    @GetMapping
    public ResponseEntity<Page<ApiKeyDto>> getPage(@PageableDefault final Pageable pageable) {
        return ResponseEntity.ok(
                apiKeyPort.getPage(pageable).map(apiKeyToApiKeyDtoMapper::map)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiKeyDto> getPage(@PathVariable Long id) {
        return ResponseEntity.of(apiKeyPort.findById(id).map(apiKeyToApiKeyDtoMapper::map));
    }


    @PutMapping
    public ResponseEntity<ApiKeyDto> updateApiKey(@RequestBody ApiKeyDto apiKeyDto) {
        return ResponseEntity.ok(
                apiKeyToApiKeyDtoMapper.map(
                        apiKeyPort.save(apiKeyDtoToApiKeyMapper.map(apiKeyDto))
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApiKey(@PathVariable Long id) {
        apiKeyPort.delete(id);
        return ResponseEntity.ok().build();
    }
}
