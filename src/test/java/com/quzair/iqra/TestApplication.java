package com.quzair.iqra;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.ZoneOffset.UTC;

@Configuration
@Import(IqraApplication.class)
public class TestApplication {

    @Bean
    @Primary
    public Clock testClock() {
        return Clock.fixed(LocalDate.of(2020,10,17).atStartOfDay(UTC).toInstant(),UTC);
    }
}
