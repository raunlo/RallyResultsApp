package com.ralohmus.rallyresults.core.ports.importer;

import org.springframework.scheduling.annotation.Scheduled;

public interface RallyResultsImporterPort {

    @Scheduled(cron ="" ) // TODO cron
    void importData();
}
