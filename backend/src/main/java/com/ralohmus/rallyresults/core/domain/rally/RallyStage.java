package com.ralohmus.rallyresults.core.domain.rally;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class RallyStage {
    private Long id;
    private Rally rally;
    private Short stageNumber;
    private String trackName;
    private BigDecimal length;
    private List<StageResult> stageResults;
}
