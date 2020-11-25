package com.ralohmus.rallyresults.core.service.ports.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetPagePort<T> {
    Page<T> getPage(Pageable pageable);
}
