package com.quzair.iqra.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GrantedAuthoritiesAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {

    private static final String USER_ROLES_HEADER = "user_roles";

    @Override
    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest httpServletRequest) {

        String userRoles = httpServletRequest.getHeader(USER_ROLES_HEADER);
        List<GrantedAuthority> authorities = StringUtils.isBlank(userRoles) ? List.<GrantedAuthority>of() : getAuthorities(userRoles);
        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(httpServletRequest,authorities);
    }

    private List<GrantedAuthority> getAuthorities(String userRoles) {
        return Set.of(userRoles.split(","))
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }
}
