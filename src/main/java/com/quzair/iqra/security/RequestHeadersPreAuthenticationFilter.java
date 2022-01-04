package com.quzair.iqra.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

public class RequestHeadersPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    private static final String USER_ID_HEADER = "user_id";

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(USER_ID_HEADER);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest httpServletRequest) {
        return StringUtils.EMPTY;
    }
}
