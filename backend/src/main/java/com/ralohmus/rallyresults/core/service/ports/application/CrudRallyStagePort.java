package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.rally.RallyStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudRallyStagePort extends CrudPort<RallyStage>, PagePort<RallyStage> {
    Page<RallyStage> getPage(Pageable pageable, Long rallyId);
}
