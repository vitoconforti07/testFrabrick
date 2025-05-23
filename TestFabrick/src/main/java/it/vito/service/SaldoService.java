package it.vito.service;

import it.vito.model.Esito;
import org.springframework.http.ResponseEntity;

public interface SaldoService {


    Esito letturaSaldo(Long idAccount);
}
