package com.ralohmus.rallyresults.core.domain.api.key;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ApiKeyOwner {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telNumber;

}
