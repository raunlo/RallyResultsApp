package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.ApiKeyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiKeyToApiKeyDtoMapper extends BaseMapper<ApiKey, ApiKeyDto> {

    @Mapping(target = "lastName", source = "apiKeyOwner.lastName")
    @Mapping(target = "firstName", source = "apiKeyOwner.firstName")
    @Mapping(target = "telNumber", source = "apiKey.apiKeyOwner.telNumber")
    @Mapping(target = "email", source = "apiKey.apiKeyOwner.email")
    @Mapping(target = "active", source = "valid")
    ApiKeyDto map(ApiKey apiKey);
}
