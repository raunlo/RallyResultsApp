package com.ralohmus.rallyresults.rest;

import com.ralohmus.rallyresults.importer.RallyResultsImporterAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final RallyResultsImporterAdapter adapter;

    @GetMapping
    public void test() {
        adapter.importData();
    }
}
