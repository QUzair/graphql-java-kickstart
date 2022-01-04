package com.quzair.iqra.resolver.publisher;

import com.quzair.iqra.domain.bank.BankAccount;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

@Component
@Slf4j
public class BankAccountPublisher {

    private final FluxProcessor<BankAccount, BankAccount> processor;
    private final FluxSink<BankAccount> sink;

    public BankAccountPublisher() {
        this.processor = DirectProcessor.<BankAccount>create().serialize();
        this.sink = processor.sink();
    }

    public void publish(BankAccount bankAccount) {
        sink.next(bankAccount);
    }

    public Publisher<BankAccount> getBankAccountPublisherFor(UUID id) {
        return processor
                .filter(bankAccount -> id.equals(bankAccount.getId()))
                .map(bankAccount -> {
            log.info("Publishing individual bank account {}", bankAccount);
            return bankAccount;
        });
    }

    public Publisher<BankAccount> getBankAccountPublisher() {
        return processor
                .map(bankAccount -> {
            log.info("Publishing bank account {}", bankAccount);
            return bankAccount;
        });
    }
}
