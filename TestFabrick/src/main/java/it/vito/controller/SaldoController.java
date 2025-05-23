package it.vito.controller;

import it.vito.model.Esito;
import it.vito.service.SaldoService;
import it.vito.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/testFabrickSaldo")
public class SaldoController {
    Logger logger = Logger.getLogger(SaldoController.class.getName());

    @Autowired
    private SaldoService saldoService;

    @GetMapping("/letturaSaldo")

    public ResponseEntity<Esito> letturaSaldo(@RequestParam("idAccount") Long idAccount) {

        try {

            Esito esito = saldoService.letturaSaldo(idAccount);
            logger.info(ErrorCode.E00.getDescrizione());
            return new ResponseEntity<>(esito, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new Esito(false, ErrorCode.E01.getDescrizione(), e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }


}
