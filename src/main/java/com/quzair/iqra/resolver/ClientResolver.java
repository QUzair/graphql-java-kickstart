package com.quzair.iqra.resolver;

import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Client;
import graphql.execution.DataFetcherResult;
import graphql.kickstart.execution.error.GenericGraphQLError;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {

    private final ExecutorService executorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public CompletableFuture<Client> client(BankAccount bankAccount) {

        return CompletableFuture.supplyAsync(
                ()-> {
                    log.info("Retrieving Client for Bank Account {}",bankAccount.getId());
                    return Client.builder()
                            .id(UUID.randomUUID())
                            .firstName("Uzair")
                            .lastName("Qureshi")
                            .build();
                }, executorService
        );


    }
}
