package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.rally.StageResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudStageResultsPort extends CrudPort<StageResult>, PagePort<StageResult> {

    Page<StageResult> getPage(Pageable pageable, Long stageId);
}
