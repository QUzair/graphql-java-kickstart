package com.quzair.iqra.resolver.mutation;

import com.quzair.iqra.domain.bank.BankAccount;
import com.quzair.iqra.domain.bank.Currency;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.Part;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class UploadFileMutation implements GraphQLMutationResolver {

    public BankAccount uploadFile(DataFetchingEnvironment dataFetchingEnvironment) {
        log.info("Uploading File");

        DefaultGraphQLServletContext context = dataFetchingEnvironment.getContext();
        if(context!=null) context.getFileParts().forEach(part -> log.info("Uploading: {}, size {}", part.getSubmittedFileName(),part.getSize()));

        return BankAccount.builder().id(UUID.randomUUID()).currency(Currency.USD).build();
    }
}
