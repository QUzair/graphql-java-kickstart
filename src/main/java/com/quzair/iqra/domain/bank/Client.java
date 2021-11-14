package com.quzair.iqra.domain.bank;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

     UUID id;
     String firstName;
     String lastName;

}
