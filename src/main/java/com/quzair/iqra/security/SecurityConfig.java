//package com.quzair.iqra.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////        http.csrf().and().cors().disable();
////                http.authorizeRequests()
////                .antMatchers("/graphql","/playground","graphiql","voyager").permitAll()
////                .anyRequest().authenticated();
////    }
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.cors().and().csrf().disable();
//        http.authorizeExchange()
//                .pathMatchers("/**").permitAll();
//        return http.build();
//    }
//
//}