package com.ralohmus.rallyresults.core.domain.rally;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@Accessors(chain = true)
public class Rally {

    private Long id;
    private String name;
    private LocalDate start;
    private LocalDate end;
    private String country;
    private List<RallyStage> rallyStages;
}
