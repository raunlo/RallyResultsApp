package com.ralohmus.rallyresults.core.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Competitor {

    private Long id;
    private String firstName;
    private String lastName;

    public boolean isImportFieldsSAme(Competitor competitor) {
        return false;
    }

}
