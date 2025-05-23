package it.vito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperazioneBancaziaDTO {


    private String transactionId;
    private String operationId;
    private String accountingDate;
    private String valueDate;
    private TypeDTO typeDTO;
    private String amount;
    private String currency;
    private String description;
    private String transactionStatus;
    private Boolean isDebit;

}
