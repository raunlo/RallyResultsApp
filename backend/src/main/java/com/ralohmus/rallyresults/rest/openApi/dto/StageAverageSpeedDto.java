package com.ralohmus.rallyresults.rest.openApi.dto;

import com.ralohmus.rallyresults.rest.admin.dto.CompetitorPairDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class StageAverageSpeedDto {
    private Long stageNumber;
    private String stageName;
    private CompetitorPairDto competitor;
    private BigDecimal averageSpeed;
}
