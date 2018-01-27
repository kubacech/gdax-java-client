package com.coinbase.exchange.api.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;


public class GdaxExchangeImpl implements GdaxExchange {

    private static Logger LOG = LoggerFactory.getLogger(GdaxExchangeImpl.class);

    private String publicKey;
    private String passphrase;
    private String baseUrl;
    private Signature signature;

    private WebClient webClient;
    private ObjectMapper objectMapper;

    public GdaxExchangeImpl(String publicKey,
                            String passphrase,
                            String baseUrl,
                            Signature signature) {
        this.publicKey = publicKey;
        this.passphrase = passphrase;
        this.baseUrl = baseUrl;
        this.signature = signature;
        this.objectMapper = new ObjectMapper();
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public <T> Mono<T> get(String resourcePath, ParameterizedTypeReference<T> responseType) {
        try {
            WebClient.RequestHeadersSpec w = webClient.get().uri(resourcePath);
            fillSecurityHeaders(resourcePath, "GET", "", w);
            return w.exchange().flatMap(clientResponse -> ((ClientResponse)clientResponse).bodyToMono(responseType));
        } catch (HttpClientErrorException ex) {
            LOG.error("GET request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public <T> Mono<List<T>> getAsList(String resourcePath, ParameterizedTypeReference<T[]> responseType) {
       Mono<T[]> result = get(resourcePath, responseType);

       return result == null ? Mono.empty() : result.map(a -> Arrays.asList(a));
    }

    @Override
    public <T> Mono<T> pagedGet(String resourcePath,
                          ParameterizedTypeReference<T> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        resourcePath += "?" + beforeOrAfter + "=" + pageNumber + "&limit=" + limit;
        return get(resourcePath, responseType);
    }

    @Override
    public <T> Mono<List<T>> pagedGetAsList(String resourcePath,
                          ParameterizedTypeReference<T[]> responseType,
                          String beforeOrAfter,
                          Integer pageNumber,
                          Integer limit) {
        Mono<T[]> result = pagedGet(resourcePath, responseType, beforeOrAfter, pageNumber, limit );
        return result == null ? Mono.empty() : result.map(a -> Arrays.asList(a));
    }

    @Override
    public <T> Mono<T> delete(String resourcePath, ParameterizedTypeReference<T> responseType) {
        try {
            WebClient.RequestHeadersSpec w = webClient.delete().uri(resourcePath);
            fillSecurityHeaders(resourcePath, "DELETE", "", w);
            return w.exchange().flatMap(clientResponse -> ((ClientResponse)clientResponse).bodyToMono(responseType));
        } catch (HttpClientErrorException ex) {
            LOG.error("DELETE request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        }
        return null;
    }

    @Override
    public <T, R> Mono<T> post(String resourcePath,  ParameterizedTypeReference<T> responseType, R jsonObj) {
        try {
            String jsonBody = objectMapper.writeValueAsString(jsonObj);
            WebClient.RequestHeadersSpec w = webClient.post().uri(resourcePath);
            fillSecurityHeaders(resourcePath, "POST", jsonBody, w);
            return w.exchange().flatMap(clientResponse -> ((ClientResponse)clientResponse).bodyToMono(responseType));
        } catch (HttpClientErrorException ex) {
            LOG.error("POST request Failed for '" + resourcePath + "': " + ex.getResponseBodyAsString());
        } catch (Exception e) {
            LOG.error("error while calling {} ", resourcePath, e);
        }
        return null;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    public void fillSecurityHeaders(String endpoint, String method, String jsonBody, WebClient.RequestHeadersSpec client) {
        String timestamp = Instant.now().getEpochSecond() + "";
        String resource = endpoint.replace(getBaseUrl(), "");

        client
            .header("accept", "application/json")
            .header("content-type", "application/json")
            .header("CB-ACCESS-KEY", publicKey)
            .header("CB-ACCESS-SIGN", signature.generate(resource, method, jsonBody, timestamp))
            .header("CB-ACCESS-TIMESTAMP", timestamp)
            .header("CB-ACCESS-PASSPHRASE", passphrase);
    }

}
