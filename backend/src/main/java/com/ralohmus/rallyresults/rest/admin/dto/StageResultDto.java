package com.ralohmus.rallyresults.rest.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StageResultDto {
    private Long id;
    private Long rallyStageId;
    private CompetitorPairDto competitor;
    private Boolean interrupted;
    private String time;
}
