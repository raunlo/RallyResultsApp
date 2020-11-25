package com.ralohmus.rallyresults.core.service.ports.application;


import org.springframework.scheduling.annotation.Scheduled;

public interface ImportRalliesPort {
    void importAndSaveRallies();
}
