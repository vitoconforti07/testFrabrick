package it.vito.feing;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeingFabrickConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeingFabrickErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Auth-Schema", "S2S");
            requestTemplate.header("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
