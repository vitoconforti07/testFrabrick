package it.vito.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import it.vito.model.ResponseFeing;

import java.io.IOException;

public class FeignFabrickErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = new String(response.body().asInputStream().readAllBytes());
            ResponseFeing responseFeing = objectMapper.readValue(body, ResponseFeing.class);


            return new FabrickResponseException(response.status(), responseFeing);
        } catch (IOException e) {
            return new RuntimeException("errore", e);
        }
    }
}
