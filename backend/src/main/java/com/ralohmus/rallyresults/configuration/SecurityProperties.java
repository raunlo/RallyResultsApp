package com.ralohmus.rallyresults.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {
    private String clientId;
    private String clientSecret;
    private String adminUserName;
    private String adminPassword;
    private String[] scopes;
    private String grantType;
    private Integer tokenValiditySeconds;
}
