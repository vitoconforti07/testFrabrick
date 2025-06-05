package it.vito.feign;

import it.vito.model.ResponseFeing;
import it.vito.model.dto.BonificoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "mockFrabrick", url = "https://sandbox.platfr.io/api/gbs/banking/v4.0", configuration = FeignFabrickConfig.class)
public interface FeignFabrick {

    @GetMapping("/accounts/{accountId}/balance")
    ResponseEntity<ResponseFeing> getSaldoFabrick(@PathVariable("accountId") Long accoundId);


    @GetMapping("/accounts/{accountId}/transactions")
    ResponseEntity<ResponseFeing> getListaMovimentiFabrick(@PathVariable("accountId") Long accountId, @RequestParam("fromAccountingDate") String fromAccountingDate, @RequestParam("toAccountingDate") String toAccountingDate);

    @PostMapping("/accounts/{accountId}/payments/money-transfers")
    ResponseEntity<ResponseFeing> bonifico(@PathVariable("accountId") Long accountId,@RequestBody BonificoRequestDTO bonificoRequestDTO);
}
