package com.ralohmus.rallyresults.core.domain.rally;

import com.ralohmus.rallyresults.core.domain.competitor.CompetitorPair;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StageResult implements Comparable<StageResult> {
    private Long id;
    private boolean interrupted;
    private String time;
    private RallyStage stage;
    private CompetitorPair competitor;

    @Override
    public int compareTo(StageResult o) {
        var splitString = time.split(":");
        var splitAnotherString = o.time.split(":");
        var compareValue = Short.valueOf(splitString[0]).compareTo(Short.valueOf(splitAnotherString[0]));
        if(compareValue != 0) return compareValue;
        return Double.valueOf(splitString[1]).compareTo(Double.valueOf(splitAnotherString[1]));
    }
}
