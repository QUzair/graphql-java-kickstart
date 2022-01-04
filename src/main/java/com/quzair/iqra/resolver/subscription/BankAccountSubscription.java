package com.quzair.iqra.resolver.subscription;

import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.resolver.publisher.BankAccountPublisher;
import graphql.kickstart.tools.GraphQLSubscriptionResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BankAccountSubscription implements GraphQLSubscriptionResolver {

    private final BankAccountPublisher publisher;

    public Publisher<BankAccount> bankAccounts(DataFetchingEnvironment dataFetchingEnvironment) {
        return publisher.getBankAccountPublisher();
    }

    public Publisher<BankAccount> bankAccount(UUID id) {
        return publisher.getBankAccountPublisherFor(id);
    }
}
