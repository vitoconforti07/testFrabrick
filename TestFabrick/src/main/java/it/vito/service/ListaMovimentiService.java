package it.vito.service;

import it.vito.model.Esito;
import it.vito.model.dto.OperazioneBancaziaDTO;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface ListaMovimentiService {
    Esito getListaMovimenti(Long accountId, Date fromAccountingDate, Date toAccountingDate, int sizePage, int nPage);
}
