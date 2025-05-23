package it.vito.controller;

import it.vito.model.Esito;
import it.vito.model.dto.BonificoRequestDTO;
import it.vito.service.BonificoService;
import it.vito.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/testFabrickSaldoBonifico")
public class BonificoController {
    Logger logger = Logger.getLogger(BonificoController.class.getName());

    @Autowired
    private BonificoService bonificoService;

    @PostMapping("/bonifico")
    public ResponseEntity<Esito> bonifico(@RequestParam("accountId") Long accountId, @RequestBody BonificoRequestDTO bonificoRequestDTO) {
        try {

            Esito esito = bonificoService.bonifico(accountId, bonificoRequestDTO);
            logger.info(ErrorCode.E00.getDescrizione());
            return new ResponseEntity<>(esito, HttpStatus.ACCEPTED);
        } catch (Exception e) {

            return new ResponseEntity<>(new Esito(false, ErrorCode.E01.getDescrizione(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
