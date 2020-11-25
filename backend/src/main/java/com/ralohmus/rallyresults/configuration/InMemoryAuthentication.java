package com.ralohmus.rallyresults.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class InMemoryAuthentication extends GlobalAuthenticationConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(securityProperties.getAdminUserName())
                .password(passwordEncoder.encode(securityProperties.getAdminPassword())).roles("ADMIN");
    }

}
