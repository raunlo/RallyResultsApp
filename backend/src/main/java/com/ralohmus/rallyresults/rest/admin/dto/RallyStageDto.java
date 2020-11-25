package com.ralohmus.rallyresults.rest.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class RallyStageDto {
    private Long id;
    private Long rallyId;
    private Short stageNumber;
    private String trackName;
    private BigDecimal length;
    private List<StageResultDto> stageResults;
}