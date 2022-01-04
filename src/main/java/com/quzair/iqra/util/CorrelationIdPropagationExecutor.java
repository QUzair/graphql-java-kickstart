package com.quzair.iqra.util;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.MDC;

import java.util.concurrent.Executor;

import static com.quzair.iqra.instrumentation.RequestLoggingInstrumentation.CORRELATION_ID;

@RequiredArgsConstructor
public class CorrelationIdPropagationExecutor implements Executor {

    private final Executor delegate;

    public static Executor wrap(Executor executor) {
        return new CorrelationIdPropagationExecutor(executor);
    }

    @Override
    public void execute(@NotNull Runnable command) {
        String correlationId = MDC.get(CORRELATION_ID);
        delegate.execute(() -> {
            try {
                MDC.put(CORRELATION_ID, correlationId);
                command.run();
            } finally {
                MDC.remove(CORRELATION_ID);
            }
        });
    }
}
