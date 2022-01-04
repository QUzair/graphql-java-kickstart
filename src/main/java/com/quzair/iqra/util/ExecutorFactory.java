package com.quzair.iqra.util;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutorFactory {

    public static Executor newExecutor() {
        var realExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var securityDelegatingExecutor = new DelegatingSecurityContextExecutorService(realExecutor);
        return CorrelationIdPropagationExecutor.wrap(securityDelegatingExecutor);
    }

}
