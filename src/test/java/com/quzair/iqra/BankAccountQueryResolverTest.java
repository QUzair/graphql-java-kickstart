package com.quzair.iqra;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import io.micrometer.core.instrument.util.IOUtils;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static java.lang.String.format;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
@EnableAutoConfiguration
class BankAccountQueryResolverTest {

    private static final String GRAPHQL_QUERY_REQUEST_PATH = "graphql/request/%s.graphql";
    private static final String GRAPHQL_QUERY_RESPONSE_PATH = "graphql/response/%s.json";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void bank_accounts_are_returned() throws IOException, JSONException {
        String testName = "bank_account";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(format(GRAPHQL_QUERY_REQUEST_PATH, testName));

        String expectedResponseBody = read(format(GRAPHQL_QUERY_RESPONSE_PATH,testName));

        Assertions.assertEquals(HttpStatus.OK,graphQLResponse.getStatusCode());
        JSONAssert.assertEquals(expectedResponseBody,graphQLResponse.getRawResponse().getBody(),true);
    }

    private String read(String location) throws IOException {
        System.out.println(location);
        return IOUtils.toString(new ClassPathResource(location).getInputStream(), StandardCharsets.UTF_8);
    }

}


