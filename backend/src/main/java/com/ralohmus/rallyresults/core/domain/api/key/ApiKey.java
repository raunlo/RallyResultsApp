package com.ralohmus.rallyresults.core.domain.api.key;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiKey {

    private Long id;
    private String value;
    private Boolean valid;
    private ApiKeyOwner apiKeyOwner;
}
