package com.ralohmus.rallyresults.rest.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompetitorDto {
    private Long id;
    private String firstName;
    private String lastName;
}
