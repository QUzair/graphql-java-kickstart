package com.quzair.iqra.repository;

import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Currency;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class BankAccountRepository {

    private static final List<BankAccount> bankAccounts  = List.of(
            BankAccount.builder()
                    .id(UUID.fromString("ab6c6803-88f8-4728-87cc-565fe1236143"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2021-10-13T07:20:50.52Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("64dfba67-20c7-452d-a07e-44d5b33b2356"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2021-10-12T07:20:50.52Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("d5999bb9-8dfd-4586-ab7c-8252b6c5fc40"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2019-12-12T07:40:50.52Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("1e90064c-20df-4bba-9051-85acf76500d6"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2019-11-12T07:20:50.52Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("4bd3745e-1a16-45fd-952d-7c87809b974c"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2019-10-12T07:20:50.52Z"))
                    .build(),
            BankAccount.builder()
                    .id(UUID.fromString("5bd3745e-1a16-45fd-952d-7c87809b974c"))
                    .currency(Currency.USD)
                    .createdAt(ZonedDateTime.parse("2019-09-12T07:20:50.52Z"))
                    .build()
    );

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public List<BankAccount> getBankAccountAfter(UUID id) {

        return getBankAccounts()
                .stream()
                .dropWhile(
                        bankAccount -> !bankAccount.getId().toString().equals(id.toString()))
                .collect(Collectors.toList());

    }
}
