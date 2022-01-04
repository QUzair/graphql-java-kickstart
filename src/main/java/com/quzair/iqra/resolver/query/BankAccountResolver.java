package com.quzair.iqra.resolver.query;

import com.quzair.iqra.context.dataloader.DataLoaderRegistryFactory;
import com.quzair.iqra.domain.bank.Asset;
import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Client;
import com.quzair.iqra.util.CorrelationIdPropagationExecutor;
import com.quzair.iqra.util.ExecutorFactory;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutorService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLResolver<BankAccount> {

    private static final Executor executor = ExecutorFactory.newExecutor();

    public CompletableFuture<List<Asset>> assets(BankAccount bankAccount) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("Getting assets for Bank Account");
            return List.of();
        }, executor);
    }


    public Client client(BankAccount bankAccount) {
        log.info("Requesting client data for bank account id {}", bankAccount.getId());
        return Client.builder()
                .id(UUID.randomUUID())
                .firstName("Uzair")
                .lastName("Qureshi")
                .build();
    }

    @PreAuthorize("hasAuthority('get:bank_account_balance')")
    public CompletableFuture<BigDecimal> balance(BankAccount bankAccount, DataFetchingEnvironment dataFetchingEnvironment) {

        DataLoader<UUID, BigDecimal> dataLoader = dataFetchingEnvironment.getDataLoader(DataLoaderRegistryFactory.BALANCE_DATA_LOADER);
        log.info("Getting Balance for {}", bankAccount.getId());
        return dataLoader.load(bankAccount.getId(), bankAccount);
    }

}
