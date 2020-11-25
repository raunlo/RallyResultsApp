package com.ralohmus.rallyresults.rest.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompetitorPairDto {
    private String id;
    private CompetitorDto driver;
    private CompetitorDto coDriver;
    private String competitionClass;
}
