package com.quzair.iqra.resolver.mutation;

import com.quzair.iqra.domain.bank.Client;
import com.quzair.iqra.domain.bank.Currency;
import com.quzair.iqra.domain.bank.input.BankAccountInput;
import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.resolver.publisher.BankAccountPublisher;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Component
@Validated
public class BankAccountMutation implements GraphQLMutationResolver {

    private final Clock clock;
    private final BankAccountPublisher publisher;

    public BankAccount createBankAccount(@Valid BankAccountInput bankAccountInput) {
        log.info("Creating Bank Account for {}",bankAccountInput);
        BankAccount bankAccount =  BankAccount.builder()
                .id(UUID.randomUUID())
                .client(Client.builder()
                        .firstName(bankAccountInput.getFirstName())
                        .lastName(bankAccountInput.getLastName())
                        .build())
                .createdAt(ZonedDateTime.now(clock))
                .createdOn(LocalDate.now(clock))
                .currency(Currency.USD)
                .build();

        publisher.publish(bankAccount);

        return bankAccount;
    }

    public BankAccount updateBankAccount(UUID id, String name, int age) {
        log.info("Updating Bank Account for {}", id);
        return BankAccount.builder()
                .id(id)
                .createdAt(ZonedDateTime.now(clock))
                .createdOn(LocalDate.now(clock))
                .currency(Currency.USD)
                .build();
    }


}
