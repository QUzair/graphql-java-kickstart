package com.quzair.iqra.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesUserDetailsService;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import javax.servlet.Filter;

@Configuration
@Slf4j
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        prePostEnabled = true
)
public class GraphQLSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(preAuthenticatedAuthenticationProvider());
    }

    private PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider = new PreAuthenticatedAuthenticationProvider();
        preAuthenticatedAuthenticationProvider.setPreAuthenticatedUserDetailsService(new PreAuthenticatedGrantedAuthoritiesUserDetailsService());

        return preAuthenticatedAuthenticationProvider;
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Securing All Endpoints");
        http    // Add the Pre Authentication Filter
                .addFilterBefore(createRequestHeadersPreAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                // All Endpoints require authentication
        .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .httpBasic().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // Actuator health endpoint for readiness, liveness checks etc
                .antMatchers("/actuator/health")
                // Permit playground for development
                .antMatchers("/voyager","/playground", "/vendor/playground/**", "/vendor/voyager/**")
                // Subscription are secured via AuthenticationConnectionListener
                .antMatchers("/subscriptions");
    }

    private Filter createRequestHeadersPreAuthenticationFilter() throws Exception {
        RequestHeadersPreAuthenticationFilter filter = new RequestHeadersPreAuthenticationFilter();
        filter.setAuthenticationDetailsSource(new GrantedAuthoritiesAuthenticationDetailsSource());
        filter.setAuthenticationManager(authenticationManager());
        filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return filter;
    }
}