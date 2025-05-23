package it.vito.feing;

import it.vito.model.ResponseFeing;
import it.vito.model.dto.BonificoRequestDTO;
import it.vito.model.dto.BonificoResponseDTO;
import it.vito.model.dto.OperazioneBancaziaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mockFrabrick", url = "https://sandbox.platfr.io/api/gbs/banking/v4.0", configuration = FeingFabrickConfig.class)
public interface FeingFabrick {

    @GetMapping("/accounts/{accountId}/balance")
    ResponseEntity<ResponseFeing> getSaldoFabrick(@PathVariable("accountId") Long accoundId);


    @GetMapping("/accounts/{accountId}/transactions")
    ResponseEntity<ResponseFeing> getListaMovimentiFabrick(@PathVariable("accountId") Long accountId, @RequestParam("fromAccountingDate") String fromAccountingDate, @RequestParam("toAccountingDate") String toAccountingDate);

    @PostMapping("/accounts/{accountId}/payments/money-transfers")
    ResponseEntity<ResponseFeing> bonifico(@PathVariable("accountId") Long accountId,@RequestBody BonificoRequestDTO bonificoRequestDTO);
}
