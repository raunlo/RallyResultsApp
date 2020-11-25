package com.ralohmus.rallyresults.rest.openApi.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CompetitorAverageSpeedDto {
    private BigDecimal averageSpeed;
    private String competitorName;
}
