package com.ralohmus.rallyresults.rest.admin.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.lang.NonNull;


@Data
@Accessors(chain = true)
public class ApiKeyDto {
    private Long id;
    private String value;
    private boolean active;
    private String firstName;
    private String lastName;
    private String email;
    private String telNumber;
}
