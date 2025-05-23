package it.vito.service;

import it.vito.model.Esito;
import it.vito.model.dto.BonificoRequestDTO;
import it.vito.model.dto.BonificoResponseDTO;

public interface BonificoService {
    Esito bonifico(Long accountId,BonificoRequestDTO bonificoRequestDTO);
}
