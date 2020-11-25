package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.rally.Rally;

import java.util.List;

public interface SearchRalliesPort {
    List<Rally> searchRallies(String name);
}
