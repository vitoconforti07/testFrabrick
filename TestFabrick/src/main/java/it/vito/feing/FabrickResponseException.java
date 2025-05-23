package it.vito.feing;

import it.vito.model.ResponseFeing;
import lombok.Getter;

@Getter
public class FabrickResponseException extends RuntimeException {
    private final int status;
    private final ResponseFeing response;

    public FabrickResponseException(int status, ResponseFeing response) {
        super("Errore Fabrick " + status + ": " + response.getStatus());
        this.status = status;
        this.response = response;
    }
}
