package com.ralohmus.rallyresults.core.domain.competitor;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CompetitorPair {
    private Long id;
    private Competitor driver;
    private Competitor coDriver;
    private String competitionClass;

    public String getCompetitorPairFullNames() {
        return driver.getFullName() + "/" + coDriver.getFullName();
    }
}
