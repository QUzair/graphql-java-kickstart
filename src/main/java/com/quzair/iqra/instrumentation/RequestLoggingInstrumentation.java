package com.quzair.iqra.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.ExecutionId;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;


@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingInstrumentation extends SimpleInstrumentation {

    private final Clock clock;
    public static final String CORRELATION_ID = "correlation_id";

    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {

        Instant start = Instant.now(clock);
        MDC.put(CORRELATION_ID, parameters.getExecutionInput().getExecutionId().toString());
        log.info("query: {} with variables {}", parameters.getQuery(),parameters.getVariables());

        return SimpleInstrumentationContext.whenCompleted((executionResult, throwable) -> {
            Duration duration = Duration.between(start, Instant.now());
            if(throwable==null) {
                log.info("Completed Successfully in: {}", duration);
            } else {
                log.warn("{} fails in {}", duration, throwable);
            }
            MDC.clear();
        });
    }
}
