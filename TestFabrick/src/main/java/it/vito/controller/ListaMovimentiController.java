package it.vito.controller;

import it.vito.model.Esito;
import it.vito.service.impl.ListaMovimentiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.logging.Logger;

@RestController
@RequestMapping("/testFabrickListaMovimenti")
@Validated
public class ListaMovimentiController {
    Logger logger = Logger.getLogger(ListaMovimentiController.class.getName());

       private ListaMovimentiServiceImpl listaMovimentiService;

    public ListaMovimentiController(ListaMovimentiServiceImpl listaMovimentiService) {
        this.listaMovimentiService = listaMovimentiService;
    }

    @GetMapping("/listaMovimenti")

    public ResponseEntity<Esito> listaMovimenti(@RequestParam @NotNull(message = "idAccount è obbligatorio") Long idAccount,
                                                @RequestParam @NotNull(message = "fromAccountingDate è obbligatorio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromAccountingDate,
                                                @RequestParam @NotNull(message = "toAccountingDate è obbligatorio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toAccountingDate,
                                                Pageable pageable) {

        int nPage = pageable.getPageNumber();
        int sizePage = pageable.getPageSize();
        if (fromAccountingDate.after(toAccountingDate)) {
            return new ResponseEntity<>(
                    new Esito(false, "Le date non sono valide", "fromAccountingDate deve essere <= toAccountingDate"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Esito listaMovimentiPage = listaMovimentiService.getListaMovimenti(idAccount, fromAccountingDate, toAccountingDate, pageable.getPageSize(), pageable.getPageNumber());
        return new ResponseEntity<>(listaMovimentiPage, HttpStatus.ACCEPTED);
    }

}
