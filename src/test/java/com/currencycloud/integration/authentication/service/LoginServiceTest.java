package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.response.TokenResponse;
import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.rest.EntityExtractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Response;

import java.io.IOException;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    private static final String API_TOKEN = "Token";

    @Mock
    private LoginClient client;

    @Mock
    private EntityExtractor extractor;


    @InjectMocks
    private LoginService testInstance;

    private static final Response<TokenResponse> TOKEN_RESPONSE = success(mock(TokenResponse.class));

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        given(client.login()).willReturn(TOKEN_RESPONSE);

        given(extractor.entityFrom(eq(TOKEN_RESPONSE), any(Function.class))).willReturn(singletonList(API_TOKEN));
    }

    @Test(expected = RestClientException.class)
    public void loginThrowsExceptionWhenClientCallFails() throws Exception {
        given(client.login()).willThrow(new IOException());

        testInstance.login();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void loginReturnsToken() throws Exception {
        given(extractor.entityFrom(any(Response.class), any(Function.class))).willReturn(API_TOKEN);

        final String token = testInstance.login();

        assertThat(token).isEqualTo(API_TOKEN);
    }


}