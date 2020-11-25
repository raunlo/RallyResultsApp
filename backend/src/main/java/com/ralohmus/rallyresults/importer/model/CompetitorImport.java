package com.ralohmus.rallyresults.importer.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompetitorImport {
    private String firstName;
    private String lastName;
}
