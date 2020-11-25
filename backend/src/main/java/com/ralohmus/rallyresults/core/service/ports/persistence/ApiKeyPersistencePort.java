package com.ralohmus.rallyresults.core.service.ports.persistence;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;

public interface ApiKeyPersistencePort extends CrudPersistencePort<ApiKey>, PagePersistencePort<ApiKey> {
}
