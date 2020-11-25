package com.ralohmus.rallyresults.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class CompetitorAverageSpeed {
    private BigDecimal averageSpeed;
    private String competitorName;
}
