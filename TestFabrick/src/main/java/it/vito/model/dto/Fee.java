package it.vito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Fee {
    private String feeCode;
    private String description;
    private double amount;
    private String currency;
}
