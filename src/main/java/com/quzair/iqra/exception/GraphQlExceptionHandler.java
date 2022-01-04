package com.quzair.iqra.exception;

import graphql.GraphQLException;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@Component
public class GraphQlExceptionHandler {

    @ExceptionHandler(GraphQLException.class)
    public ThrowableGraphQLError handle(GraphQLException e) {
        return  new ThrowableGraphQLError(e);
    }

    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError handleRuntimeException(RuntimeException e) {
        return  new ThrowableGraphQLError(e,"Oh no, our code, it's broken");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ThrowableGraphQLError handleAccessDenied(AccessDeniedException e) {
        return  new ThrowableGraphQLError(e,"Oh no, you aren't meant to be here");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ThrowableGraphQLError handleRuntimeException(ConstraintViolationException e) {
        return  new ThrowableGraphQLError(e);
    }
}
