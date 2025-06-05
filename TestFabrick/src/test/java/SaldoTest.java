import it.vito.AppBancaFabrickApplication;
import it.vito.feign.FeignFabrick;
import it.vito.model.Error;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.SaldoDTO;
import it.vito.model.entity.ContoCorrente;
import it.vito.repository.ContoCorrenteRepository;
import it.vito.service.impl.SaldoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = AppBancaFabrickApplication.class)
@ComponentScan(basePackages = "it.vito")
public class SaldoTest {

    @InjectMocks
    private SaldoServiceImpl saldoService;

    @Mock
    private FeignFabrick feignFabrick;
    @Mock
    private ContoCorrenteRepository contoCorrenteRepository;


    @Test
    public void testGetSaldoTrue() {

        Long accountId = 1L;

        SaldoDTO  saldoDTO = new SaldoDTO();
        saldoDTO.setDate("");
        saldoDTO.setBalance("2");
        saldoDTO.setAvailableBalance("2");
        saldoDTO.setCurrency("2");

        ResponseFeing  responseFeing = new  ResponseFeing();
        responseFeing.setStatus("OK");
        responseFeing.setErrors(null);
        responseFeing.setPayload(saldoDTO);
        ResponseEntity<ResponseFeing> responseFeingResponseEntity = new ResponseEntity<>(responseFeing, HttpStatus.OK);
        when(feignFabrick.getSaldoFabrick(accountId)).thenReturn(responseFeingResponseEntity);

        ContoCorrente contoCorrente = new ContoCorrente(1, 1, 1.00, new Date(2025-04-23), null);
        when(contoCorrenteRepository.findByidAccount(Math.toIntExact(accountId))).thenReturn(Optional.of(contoCorrente));
        Esito esito = saldoService.letturaSaldo(accountId);
        SaldoDTO payloadSaldoDTO = (SaldoDTO) esito.getExtraParams();
        Double saldo = Double.valueOf(payloadSaldoDTO.getBalance());
        assertEquals(saldo, 2);


    }

    @Test
    public void testGetSaldoFalse() {

        Long accountId = 0L;

        ResponseFeing  responseFeing = new  ResponseFeing();
        responseFeing.setStatus("KO");
        responseFeing.setErrors(List.of(new Error("REQ004", "Invalid account identifier", "")));
        responseFeing.setPayload(null);
        ResponseEntity<ResponseFeing> responseFeingResponseEntity = new ResponseEntity<>(responseFeing, HttpStatus.FORBIDDEN);

//        ResponseEntity responseEntitySaldo = new ResponseEntity(responseFeingResponseEntity, HttpStatus.FORBIDDEN);
        when(feignFabrick.getSaldoFabrick(accountId)).thenReturn(responseFeingResponseEntity);


        Esito esito = saldoService.letturaSaldo(accountId);

        String payload = (String)  esito.getExtraParams();

        assertEquals(payload, "Invalid account identifier");


    }
}

