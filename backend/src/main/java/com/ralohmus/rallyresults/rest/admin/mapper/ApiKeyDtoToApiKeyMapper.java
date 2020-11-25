package com.ralohmus.rallyresults.rest.admin.mapper;

import com.ralohmus.rallyresults.core.domain.api.key.ApiKey;
import com.ralohmus.rallyresults.core.domain.api.key.ApiKeyOwner;
import com.ralohmus.rallyresults.core.mapper.BaseMapper;
import com.ralohmus.rallyresults.rest.admin.dto.ApiKeyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApiKeyDtoToApiKeyMapper extends BaseMapper<ApiKeyDto, ApiKey> {

    @Override
    @Mapping(target = "valid", source = "active")
    @Mapping(target = "apiKeyOwner", expression = "java(mapKeyOwner(apiKeyDto))")
    ApiKey map(ApiKeyDto apiKeyDto);

    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "telNumber", target = "telNumber")
    ApiKeyOwner mapKeyOwner(ApiKeyDto apiKeyDto);
}
