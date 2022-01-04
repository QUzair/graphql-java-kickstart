package com.quzair.iqra.domain.bank;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
public class BankAccount {

    UUID id;
    Client client;
    Currency currency;
    ZonedDateTime createdAt;
    LocalDate createdOn;
    BigDecimal balance;


}
