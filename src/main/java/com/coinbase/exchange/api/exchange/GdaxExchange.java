package com.coinbase.exchange.api.exchange;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface GdaxExchange {

    String getBaseUrl();

    <T> Mono<T> get(String endpoint, ParameterizedTypeReference<T> type);

    <T> Mono<T> pagedGet(String endpoint, ParameterizedTypeReference<T> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);

    <T> Mono<List<T>> getAsList(String endpoint, ParameterizedTypeReference<T[]> type);

    <T> Mono<List<T>> pagedGetAsList(String endpoint, ParameterizedTypeReference<T[]> responseType, String beforeOrAfter, Integer pageNumber, Integer limit);

    <T, R> Mono<T> post(String endpoint, ParameterizedTypeReference<T> type, R jsonObject);

    <T> Mono<T> delete(String endpoint, ParameterizedTypeReference<T> type);
}
