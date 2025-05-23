package it.vito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SaldoDTO {


    private String date;
    private String balance;
    private String availableBalance;
    private String currency;
}
