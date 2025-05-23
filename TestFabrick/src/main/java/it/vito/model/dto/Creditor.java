package it.vito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Creditor {

    private String name;
    private Account account;
    private Address address;
}

