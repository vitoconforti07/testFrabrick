package it.vito.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.vito.feing.FabrickResponseException;
import it.vito.feing.FeingFabrick;
import it.vito.model.Error;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.OperazioneBancaziaDTO;
import it.vito.model.entity.OperazioneBancaria;
import it.vito.repository.OperazioneBancariaRepository;
import it.vito.service.ListaMovimentiService;
import it.vito.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ListaMovimentiServiceImpl implements ListaMovimentiService {

    private static final Logger logger = LoggerFactory.getLogger(ListaMovimentiServiceImpl.class);
    @Autowired
    private FeingFabrick feingFabrick;
    @Autowired
    private OperazioneBancariaRepository operazioneBancariaRepository;

    @Override
    public Esito getListaMovimenti(Long accountId, Date fromAccountingDate, Date toAccountingDate, int sizePage, int nPage) {
        Esito esito = new Esito();


        Date today = new Date();
        if (toAccountingDate.after(today)) {
            logger.error(ErrorCode.E02.getDescrizione());
            throw new RuntimeException(ErrorCode.E02.getDescrizione());
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String fromAccountingDateString = formatter.format(fromAccountingDate);
        String toAccountingDateString = formatter.format(toAccountingDate);

        List<OperazioneBancaziaDTO> subList = new ArrayList<>();

        ResponseEntity<ResponseFeing> listaMovimentiFabrickResponse = null;

        try {
            listaMovimentiFabrickResponse = feingFabrick.getListaMovimentiFabrick(accountId, fromAccountingDateString, toAccountingDateString);
        } catch (FabrickResponseException ex) {
            ResponseFeing errore = ex.getResponse();
            listaMovimentiFabrickResponse = new ResponseEntity<>(errore, HttpStatus.valueOf(ex.getStatus()));
        }

        HttpStatus statusCode = listaMovimentiFabrickResponse.getStatusCode();
        List<OperazioneBancaziaDTO> operazioneBancaziaDTOList = new ArrayList<>();
        Predicate<HttpStatus> flagStatusCodeTrue = httpStatus -> httpStatus.value() >= 200 && httpStatus.value() < 300;

        ResponseFeing responseBody = listaMovimentiFabrickResponse.getBody();
        BiPredicate<ResponseFeing, String> flagResponseBody = (responseFeing, status) -> responseFeing != null && responseFeing.getStatus().equalsIgnoreCase(status);
        if (flagStatusCodeTrue.test(statusCode) && flagResponseBody.test(responseBody, "OK")) {

            ObjectMapper objectMapper = new ObjectMapper();
            Object payload = responseBody.getPayload();
//            LinkedHashMap<String, Object> listaMappe = objectMapper.convertValue(
//                    payload,
//                    new TypeReference<LinkedHashMap<String, Object>>()  {}
//            );


            operazioneBancaziaDTOList = objectMapper.convertValue(
                    payload,
                    new TypeReference<List<OperazioneBancaziaDTO>>() {}
            );
            int start = nPage * sizePage;
            int end = Math.min(start + sizePage, operazioneBancaziaDTOList.size());
            subList = operazioneBancaziaDTOList.subList(start, end);

            esito.setEsito(true);
            esito.setMessaggio("");
            esito.setExtraParams(subList);

            //qui aggiungiamo la lista dei movimenti usando gli id transizioni e togliendo quelli
            // e dali salvare


        } else if (!flagStatusCodeTrue.test(statusCode) && flagResponseBody.test(responseBody, "KO") && responseBody.getErrors() != null) {
            List<String> collect = responseBody.getErrors().stream().map(Error::getDescription).collect(Collectors.toList());
            esito.setEsito(false);
            esito.setMessaggio(ErrorCode.E01.getDescrizione());
            esito.setExtraParams(String.valueOf(collect.get(0)));


        } else if (!flagStatusCodeTrue.test(statusCode)) {
            esito.setEsito(false);
            esito.setMessaggio(ErrorCode.E01.getDescrizione());
            esito.setExtraParams(responseBody.getErrors().get(0).getDescription());

        } return esito;
    }


}
