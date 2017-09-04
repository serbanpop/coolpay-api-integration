package com.currencycloud.rest;

import retrofit2.Response;

import java.util.function.Function;

import static java.util.Optional.ofNullable;

public class EntityExtractor {

    public <R, E> E entityFrom(Response<R> response, Function<R, E> mapper) {
        if (response.isSuccessful()) {
            return ofNullable(response.body())
                    .map(mapper)
                    .orElseThrow(() -> new IllegalStateException("No body returned by the recipients service"));
        }
        throw new RuntimeException("Failed service call. Status code: " + response.code());
    }

}
