package org.leha.commons.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.*;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableConfigurationProperties(AccessControlConfigurationProperties.class)
@EnableMethodSecurity
@EnableWebSecurity
@Slf4j
@RequiredArgsConstructor
public class AccessControlConfiguration {

    private static final String PATTERN = "/users/**" ;
    private final AccessControlConfigurationProperties accessControlConfigurationProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        log.info("creating service to service security filter chain") ;
        return httpSecurity.headers( header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(CsrfConfigurer::disable)
                .securityMatcher(PATTERN)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .anonymous(AnonymousConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authRequest -> authRequest.anyRequest().authenticated())
                .addFilterBefore(new UserContextRequestFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .build();
    }



    private void configureCommonSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers( header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(CsrfConfigurer::disable)
                .securityMatcher(PATTERN)
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .anonymous(AnonymousConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authRequest -> authRequest.anyRequest().authenticated())
                ;

    }

    @Bean
    public UserContextAccessor userContextAccessor(){
        return new UserContextAccessor() ;
    }



}
