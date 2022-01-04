package com.quzair.iqra.service;

import com.quzair.iqra.domain.bank.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BalanceService {


    public Map<UUID, BigDecimal> getBalanceFor(Map<UUID, BankAccount> bankAccountIds, String userId) {
        log.info("Requesting Bank Account Ids {} for user {}", bankAccountIds,userId);

        Set<UUID> uuids = bankAccountIds.keySet();

        // Request balance for ids
        // Base on balance response -> Validate BankAccountIds

        return uuids.stream().collect(Collectors.toMap(id-> id,id->BigDecimal.valueOf(id.variant())));
    }
}
