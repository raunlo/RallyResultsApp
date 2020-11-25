package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.api.key.ApiKeyDbo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ApiKeyDboToApiKeyMapper extends BaseMapper<ApiKeyDbo, ApiKey> {

    ApiKey map(ApiKeyDbo apiKeyDbo);
}
