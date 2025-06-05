package it.vito.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.vito.feign.FabrickResponseException;
import it.vito.feign.FeignFabrick;
import it.vito.model.Error;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.SaldoDTO;
import it.vito.model.entity.ContoCorrente;
import it.vito.repository.ContoCorrenteRepository;
import it.vito.service.SaldoService;
import it.vito.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class SaldoServiceImpl implements SaldoService {
    private static final Logger logger = LoggerFactory.getLogger(SaldoServiceImpl.class);
    @Autowired
    private FeignFabrick feignFabrick;

    @Autowired
    private ContoCorrenteRepository contoCorrenteRepository;

    @Override
    public Esito letturaSaldo(Long idAccount) {

        ResponseEntity<ResponseFeing> saldoFabrickResponse = null;
        Esito esito = new Esito();


        try {

            saldoFabrickResponse = feignFabrick.getSaldoFabrick(idAccount);
        } catch (FabrickResponseException ex) {
            ResponseFeing errore = ex.getResponse();

            saldoFabrickResponse = new ResponseEntity<>(errore, HttpStatus.valueOf(ex.getStatus()));

        }

        HttpStatus statusCode = saldoFabrickResponse.getStatusCode();
        Predicate<HttpStatus> flagStatusCodeTrue = httpStatus -> httpStatus.value() >= 200 && httpStatus.value() < 300;

        ResponseFeing responseBody = saldoFabrickResponse.getBody();
        BiPredicate<ResponseFeing, String> flagResponseBody = (responseFeing, status) -> responseFeing != null && responseFeing.getStatus().equalsIgnoreCase(status);


        if (flagStatusCodeTrue.test(statusCode) && flagResponseBody.test(responseBody, "OK")) {

            ObjectMapper mapper = new ObjectMapper();
            if (responseBody.getPayload() == null) {
                esito.setEsito(false);
                esito.setMessaggio(ErrorCode.E00.getDescrizione());
                esito.setExtraParams(ErrorCode.E03.getDescrizione());
            }
            SaldoDTO payloadSaldoDTO = mapper.convertValue(responseBody.getPayload(), SaldoDTO.class);


            esito.setEsito(true);
            esito.setMessaggio("Operazione eseguita");
            esito.setExtraParams(payloadSaldoDTO);
            Optional<ContoCorrente> byidAccount = contoCorrenteRepository.findByidAccount(Math.toIntExact(idAccount));
            if (byidAccount.isPresent()) {
                ContoCorrente contoCorrente = byidAccount.get();
                String balance = payloadSaldoDTO.getBalance();
                contoCorrente.setSaldo(Double.valueOf(balance));
                Date today = new Date();

                contoCorrente.setDataUltimoAggiornamento(today);
                contoCorrenteRepository.saveAndFlush(contoCorrente);
            }

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
