package it.vito.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vito.feign.FabrickResponseException;
import it.vito.feign.FeignFabrick;
import it.vito.model.Error;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.BonificoRequestDTO;
import it.vito.model.dto.BonificoResponseDTO;
import it.vito.service.BonificoService;
import it.vito.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class BonificoServiceImpl implements BonificoService {

    private static final Logger logger = LoggerFactory.getLogger(BonificoServiceImpl.class);
    @Autowired
    FeignFabrick feignFabrick;

    @Override
    public Esito bonifico(Long accountId,BonificoRequestDTO bonificoRequestDTO) {


        ResponseEntity<ResponseFeing> bonificoResponseEntity = null;
        Esito esito = new Esito();

        try {

            bonificoResponseEntity = feignFabrick.bonifico(accountId, bonificoRequestDTO);
        } catch (FabrickResponseException ex) {
            ResponseFeing errore = ex.getResponse();

            bonificoResponseEntity = new ResponseEntity<>(errore, HttpStatus.valueOf(ex.getStatus()));

        }


        HttpStatus statusCode = bonificoResponseEntity.getStatusCode();
        Predicate<HttpStatus> flagStatusCodeTrue = httpStatus -> httpStatus.value() >= 200 && httpStatus.value() < 300;

        ResponseFeing responseBody = bonificoResponseEntity.getBody();
        BiPredicate<ResponseFeing, String> flagResponseBody = (responseFeing, status) -> responseFeing != null && responseFeing.getStatus().equalsIgnoreCase(status);


        if (flagStatusCodeTrue.test(statusCode) && flagResponseBody.test(responseBody, "OK")) {

            ObjectMapper mapper = new ObjectMapper();
            if (responseBody.getPayload() == null) {
                esito.setEsito(false);
                esito.setMessaggio(ErrorCode.E00.getDescrizione());
                esito.setExtraParams(ErrorCode.E03.getDescrizione());
            }
            BonificoResponseDTO bonificoResponseDTO = mapper.convertValue(responseBody.getPayload(), BonificoResponseDTO.class);


            esito.setEsito(true);
            esito.setMessaggio("Operazione eseguita");
            esito.setExtraParams(bonificoResponseDTO);


        } else if (!flagStatusCodeTrue.test(statusCode) && flagResponseBody.test(responseBody, "KO") && responseBody.getErrors() != null) {

            List<String> collect = responseBody.getErrors().stream().map(Error::getDescription).collect(Collectors.toList());
            esito.setEsito(false);
            esito.setMessaggio(ErrorCode.E00.getDescrizione());
            esito.setExtraParams(String.valueOf(collect.get(0)));


        } else if (!flagStatusCodeTrue.test(statusCode)) {
            esito.setEsito(false);
            esito.setMessaggio(ErrorCode.E00.getDescrizione());
            esito.setExtraParams(responseBody.getErrors().get(0).getDescription());

        }
        return esito;
    }
}

