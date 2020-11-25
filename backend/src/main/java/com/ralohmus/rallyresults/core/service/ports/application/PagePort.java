package com.ralohmus.rallyresults.core.service.ports.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PagePort<TDomain> {
    Page<TDomain> getPage(Pageable pageable);
}
