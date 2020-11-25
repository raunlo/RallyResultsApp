package com.ralohmus.rallyresults.rest.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public interface CurrentURI {
    default UriComponentsBuilder getCurrentUriWithId() {
       return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("{id}");
    }
}
