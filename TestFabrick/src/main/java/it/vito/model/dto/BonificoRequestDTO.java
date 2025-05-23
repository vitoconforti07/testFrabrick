package it.vito.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BonificoRequestDTO {

    private Long accountId;

    //il beneficiario del bonifico;
    @JsonProperty("creditor.name")
    private String creditorName;

    //    il beneficiario del bonifico;
    @JsonProperty("creditor.account.accountCode")
    private String creditorAccountAccountCode;

    //    descrizione del bonifico
    private String description;
    private String currency;
    private String amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    private String executionDate;
}


