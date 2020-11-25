package com.ralohmus.rallyresults.persistence.mapper;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.domain.api.key.ApiKeyOwner;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.persistence.entities.api.key.ApiKeyDbo;
import com.ralohmus.rallyresults.persistence.entities.api.key.ApiKeyOwnerDbo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiKeyToApiKeyDboMapper extends BaseMapper<ApiKey, ApiKeyDbo> {

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    ApiKeyDbo map(ApiKey apiKey);

    @Mapping(target = "modifiedDate", ignore = true)
    @Mapping(target = "modifiedBy",ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    ApiKeyOwnerDbo map(ApiKeyOwner apiKeyOwner);
}
