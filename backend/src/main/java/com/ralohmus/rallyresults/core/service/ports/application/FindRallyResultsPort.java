package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.rally.Rally;
import com.ralohmus.rallyresults.core.domain.request.RallyResultsSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindRallyResultsPort {

    Page<Rally> findRallyResults(RallyResultsSearchRequest request, Pageable pageable);

}
