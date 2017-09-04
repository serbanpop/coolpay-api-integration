package com.currencycloud.integration.authentication.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class TokenRequest {

    private String username;

    private String apikey;

}
