package com.ralohmus.rallyresults.rest.util;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Error {

    private String  error;
    private Integer code;
}
