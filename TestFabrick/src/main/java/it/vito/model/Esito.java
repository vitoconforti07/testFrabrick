package it.vito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Esito {


    private boolean esito;

    private String messaggio;

    private Object extraParams;
}
