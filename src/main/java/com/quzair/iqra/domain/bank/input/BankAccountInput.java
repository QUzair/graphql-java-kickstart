package com.quzair.iqra.domain.bank.input;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class BankAccountInput {


    String firstName;
    @NotBlank
    String lastName;
    int age;
}
