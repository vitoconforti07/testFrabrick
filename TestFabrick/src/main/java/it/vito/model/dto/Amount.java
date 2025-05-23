package it.vito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class Amount {
    private int debtorAmount;
    private String debtorCurrency;
    private int creditorAmount;
    private String creditorCurrency;
    private String creditorCurrencyDate;
    private int exchangeRate;
}
