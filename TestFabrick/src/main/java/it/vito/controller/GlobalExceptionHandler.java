package it.vito.controller;

import it.vito.model.Esito;
import it.vito.utils.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Esito> handleGenericException(Exception ex) {
        Esito esito = new Esito(false, ErrorCode.E00.getDescrizione(), ex.getMessage());
        return new ResponseEntity<>(esito, HttpStatus.NOT_FOUND);
    }


}

