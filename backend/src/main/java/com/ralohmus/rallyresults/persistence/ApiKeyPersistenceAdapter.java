package com.ralohmus.rallyresults.persistence;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.service.ports.persistence.ApiKeyPersistencePort;
import com.ralohmus.rallyresults.persistence.entities.api.key.ApiKeyDbo;
import com.ralohmus.rallyresults.persistence.mapper.ApiKeyDboToApiKeyMapper;
import com.ralohmus.rallyresults.persistence.mapper.ApiKeyToApiKeyDboMapper;
import com.ralohmus.rallyresults.persistence.repo.ApiKeyRepository;
import org.springframework.stereotype.Component;

@Component
public class ApiKeyPersistenceAdapter extends BasePersistenceAdapter<ApiKey, ApiKeyDbo> implements ApiKeyPersistencePort{


    protected ApiKeyPersistenceAdapter(ApiKeyRepository repository,
                                       ApiKeyDboToApiKeyMapper apiKeyDboToApiKeyMapper,
                                       ApiKeyToApiKeyDboMapper apiKeyToApiKeyDboMapper) {
        super(repository, apiKeyDboToApiKeyMapper, apiKeyToApiKeyDboMapper);
    }
}
