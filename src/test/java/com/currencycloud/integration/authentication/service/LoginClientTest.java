package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.request.TokenRequest;
import com.currencycloud.integration.authentication.response.TokenResponse;
import com.currencycloud.integration.authentication.service.LoginClient.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class LoginClientTest {

    @Mock
    private HttpClient httpClient;

    @Mock
    private Call<TokenResponse> tokenResponseCall;

    private Response<TokenResponse> tokenResponse = success(mock(TokenResponse.class));

    @Spy
    private LoginClient testInstance = new LoginClient();

    @Before
    public void setUp() throws Exception {
        given(testInstance.client()).willReturn(httpClient);

        given(httpClient.login(any(TokenRequest.class))).willReturn(tokenResponseCall);
        given(tokenResponseCall.execute()).willReturn(tokenResponse);
    }

    @Test
    public void loginReturnsReponse() throws Exception {
        final Response<TokenResponse> response = testInstance.login();

        assertThat(response).isEqualTo(tokenResponse);
    }

    @Test
    public void loginInvokesHttpClient() throws Exception {
        testInstance.login();

        then(httpClient).should().login(any(TokenRequest.class));
    }
}