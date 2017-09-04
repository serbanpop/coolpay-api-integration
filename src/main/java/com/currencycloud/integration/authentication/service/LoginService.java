package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.response.TokenResponse;
import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.rest.EntityExtractor;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class LoginService {

    private final LoginClient client;
    private final EntityExtractor extractor;

    public String login() {
        try {
            return extractor.entityFrom(client.login(), TokenResponse::getToken);
        } catch (IOException e) {
            throw new RestClientException("Failed to login", e);
        }
    }

}
