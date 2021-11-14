package com.quzair.iqra.resolver.query;

import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Client;
import com.quzair.iqra.domain.bank.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class BankAccountResolver implements GraphQLQueryResolver {

    public BankAccount bankAccount(UUID id) {
        log.info("Retrieving bank account id: {}", id);
        return BankAccount.builder().id(id)
                .currency(Currency.USD).build();
    }

}
