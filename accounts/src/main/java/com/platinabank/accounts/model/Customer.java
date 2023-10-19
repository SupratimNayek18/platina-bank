package com.platinabank.accounts.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {

    @Id
    private int customerId;

    private String firstName;

    private String lastName;

    private String email;

    private Long mobileNumber;

}
