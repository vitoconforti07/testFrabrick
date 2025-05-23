import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.vito.AppBancaFabrickApplication;
import it.vito.feing.FeingFabrick;
import it.vito.model.Esito;
import it.vito.model.ResponseFeing;
import it.vito.model.dto.OperazioneBancaziaDTO;
import it.vito.repository.OperazioneBancariaRepository;
import it.vito.service.impl.ListaMovimentiServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = AppBancaFabrickApplication.class)
@ComponentScan(basePackages = "it.vito")
public class ListaMovimentiTest {


    @InjectMocks
    private ListaMovimentiServiceImpl listaMovimentiService;

    @Mock
    private FeingFabrick feingFabrick;

    @Mock
    private OperazioneBancariaRepository operazioneBancariaRepository;

    @Test
    public void testGetListaMovimenti() throws ParseException {
        Gson gson = new Gson();

        List<OperazioneBancaziaDTO> g = List.of(new OperazioneBancaziaDTO(), new OperazioneBancaziaDTO());
        String s = gson.toJson(g);

        Type listType = new TypeToken<List<OperazioneBancaziaDTO>>() {
        }.getType();
        List<OperazioneBancaziaDTO> operazioneBancaziaDTOList = gson.fromJson(UtilTest.jsonStringListaMovimenti, listType);
        ResponseFeing responseFeing = new ResponseFeing("OK",new ArrayList<>(),operazioneBancaziaDTOList);
        ResponseEntity<ResponseFeing> responseEntity = new ResponseEntity<>(responseFeing, HttpStatus.OK);

        when(feingFabrick.getListaMovimentiFabrick(14537780L, "2019-04-01", "2019-04-01")).thenReturn(responseEntity);


        Date fromAccountingDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-01");
        Date toAccountingDate = new SimpleDateFormat("yyyy-MM-dd").parse("2019-04-01");

       Esito esito = listaMovimentiService.getListaMovimenti(14537780L, fromAccountingDate, toAccountingDate, 2, 0);
        List<OperazioneBancaziaDTO> listaOperazioni = (List<OperazioneBancaziaDTO>) esito.getExtraParams();
        assertFalse(listaOperazioni.isEmpty());
    }


}
