package com.quzair.iqra.resolver.query;

import com.quzair.iqra.connection.CursorUtil;
import com.quzair.iqra.context.CustomGraphQLContext;
import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Client;
import com.quzair.iqra.domain.bank.Currency;
import com.quzair.iqra.repository.BankAccountRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Null;
import java.time.Clock;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class BankAccountQueryResolver implements GraphQLQueryResolver {

    private final BankAccountRepository bankAccountRepository;
    private final CursorUtil cursorUtil;
    private final Clock clock;

    @PreAuthorize("hasAuthority('get:bank_account')")
    public BankAccount bankAccount(UUID id, DataFetchingEnvironment dataFetchingEnvironment) {

        CustomGraphQLContext context = dataFetchingEnvironment.getContext();
        log.info("UserId: {}", context.getUserId());

        log.info("Retrieving bank account id: {}", id);
        return BankAccount.builder().id(id)
                .currency(Currency.USD)
                .createdAt(ZonedDateTime.now(clock))
                .createdOn(LocalDate.now(clock))
                .build();
    }

    public Connection<BankAccount> bankAccounts(int first, @Nullable String cursor) {

        List<Edge<BankAccount>> edges = getBankAccounts(cursor)
                .stream()
                .map(bankAccount -> new DefaultEdge<>(bankAccount, cursorUtil.createCursor(bankAccount.getId())))
                .limit(first)
                .collect(Collectors.toUnmodifiableList());

        ConnectionCursor firstCursor = cursorUtil.getFirstCursorFrom(edges);
        ConnectionCursor lastCursor = cursorUtil.getLastCursorFrom(edges);
        PageInfo pageInfo = new DefaultPageInfo(firstCursor, lastCursor, cursor != null, edges.size() >= first);

        return new DefaultConnection<>(edges, pageInfo);
    }



    public List<BankAccount> getBankAccounts(String cursor) {
        if (cursor == null) {
            return bankAccountRepository.getBankAccounts();
        }
        return bankAccountRepository.getBankAccountAfter(cursorUtil.decodeCursor(cursor));
    }

}
