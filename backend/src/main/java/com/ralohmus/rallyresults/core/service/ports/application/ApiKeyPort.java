package com.ralohmus.rallyresults.core.service.ports.application;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;

public interface ApiKeyPort extends CrudPort<ApiKey>, GetPagePort<ApiKey> {
}
