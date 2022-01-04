package com.quzair.iqra.context.dataloader;

import com.quzair.iqra.service.BalanceService;
import com.quzair.iqra.util.CorrelationIdPropagationExecutor;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class DataLoaderRegistryFactory {

    public static final String BALANCE_DATA_LOADER = "BALANCE_DATA_LOADER";
    private static final Executor balanceThreadPool = CorrelationIdPropagationExecutor.wrap(
            new DelegatingSecurityContextExecutorService(
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())));
    private final BalanceService balanceService;

    public DataLoaderRegistry create(String userId) {
        DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();

        dataLoaderRegistry.register(BALANCE_DATA_LOADER, createBalanceDataLoader(userId));
        return dataLoaderRegistry;
    }

    private DataLoader<UUID, BigDecimal> createBalanceDataLoader(String userId) {
        return DataLoader.newMappedDataLoader((bankAccountIds,environment) -> CompletableFuture.supplyAsync(() ->
                balanceService.getBalanceFor((Map) environment.getKeyContexts(), userId),
                balanceThreadPool));
    }
}
