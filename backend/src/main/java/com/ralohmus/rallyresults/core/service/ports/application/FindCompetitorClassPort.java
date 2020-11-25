package com.ralohmus.rallyresults.core.service.ports.application;

import java.util.List;

public interface FindCompetitorClassPort {
    List<String> getAllCompetitorClasses(String name);
}
