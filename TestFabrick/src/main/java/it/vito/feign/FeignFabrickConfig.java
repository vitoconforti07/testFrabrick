package it.vito.feign;

import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignFabrickConfig {

    @Value("${feignFabrick.authSchema}")
    private String authSchema;

    @Value("${feignFabrick.apiKey}")
    private String apiKey;

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignFabrickErrorDecoder();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Auth-Schema", authSchema);
            requestTemplate.header("Api-Key", apiKey);
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("Content-Type", "application/json");
        };
    }
}
