package it.vito.utils;

public enum ErrorCode {


    E00("E01", "Operazione fallita"),
    E02("E02", "Data di Fine Lista Movimenti posteriore alla data di oggi "),
    E03("E03", "Oggeto vuoto");


    private final String codice;
    private final String descrizione;

    ErrorCode(String codice, String descrizione) {
        this.codice = codice;
        this.descrizione = descrizione;
    }

    public String getCodice() {
        return codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

}


