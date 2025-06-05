import com.google.gson.Gson;
import it.vito.AppBancaFabrickApplication;
import it.vito.feign.FeignFabrick;
import it.vito.model.Error;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.BonificoRequestDTO;
import it.vito.service.impl.BonificoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = AppBancaFabrickApplication.class)
@ComponentScan(basePackages = "it.vito")
public class BonificoTest {

    @InjectMocks
    private BonificoServiceImpl bonificoService;

    @Mock
    private FeignFabrick feignFabrick;



    @Test
    public void testGetSaldoTrue() {

        Long accountId = 1L;

        Gson gson = new Gson();
        BonificoRequestDTO bonificoRequestDTO = gson.fromJson(UtilTest.jsonStringBonifico, BonificoRequestDTO.class);

        Error error = gson.fromJson(UtilTest.errorBonifico, Error.class);

        ResponseFeing  responseFeing = new  ResponseFeing();
        responseFeing.setStatus("KO");
        responseFeing.setErrors(List.of(error));
        responseFeing.setPayload(null);
        ResponseEntity<ResponseFeing> responseFeingResponseEntity = new ResponseEntity<>(responseFeing, HttpStatus.FORBIDDEN);
        when(feignFabrick.bonifico(accountId, bonificoRequestDTO)).thenReturn(responseFeingResponseEntity);


        Esito esito = bonificoService.bonifico(accountId, bonificoRequestDTO);

        assertEquals(esito.getExtraParams(), "it.sella.pagamenti.servizibonifico.exception.ServiziInvioBonificoSubsystemException: it.sella.pagamenti.sottosistemi.SottosistemiException: Errore tecnico CONTO 45685475:Conto 45685475 non esiste");


    }

}

