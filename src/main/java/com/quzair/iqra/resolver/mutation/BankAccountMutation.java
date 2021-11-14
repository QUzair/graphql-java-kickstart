package com.quzair.iqra.resolver.mutation;

import com.quzair.iqra.domain.bank.Currency;
import com.quzair.iqra.domain.bank.input.BankAccountInput;
import com.quzair.iqra.domain.bank.BankAccount;
import graphql.kickstart.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BankAccountMutation implements GraphQLMutationResolver {

    public BankAccount createBankAccount(BankAccountInput bankAccountInput) {
        return BankAccount.builder()
                .id(UUID.randomUUID())
                .currency(Currency.USD)
                .build();
    }


}
