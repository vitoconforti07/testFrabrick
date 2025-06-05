package it.vito.controller;

import it.vito.model.Esito;
import it.vito.service.SaldoService;
import it.vito.service.impl.SaldoServiceImpl;
import it.vito.utils.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.logging.Logger;

@RestController
@RequestMapping("/testFabrickSaldo")
@Validated
public class SaldoController {
    Logger logger = Logger.getLogger(SaldoController.class.getName());


    public SaldoController(SaldoServiceImpl saldoService) {
        this.saldoService = saldoService;
    }

    private SaldoServiceImpl saldoService;

    @GetMapping("/letturaSaldo")

    public ResponseEntity<Esito> letturaSaldo(@RequestParam("idAccount") @NotNull(message = "accountId è obbligatorio") Long idAccount) {
        Esito esito = saldoService.letturaSaldo(idAccount);
        logger.info("Operazione eseguita");
        return new ResponseEntity<>(esito, HttpStatus.OK);

    }


}
