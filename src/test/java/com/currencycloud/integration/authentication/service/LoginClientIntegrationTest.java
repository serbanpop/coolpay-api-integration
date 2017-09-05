package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.response.TokenResponse;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.http.Fault.CONNECTION_RESET_BY_PEER;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginClientIntegrationTest {

    private static final String TOKEN = "aff06fec-e041-4994-849e-223f0569e0bc";

    private LoginClient testInstance = new LoginClient();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void isSuccessful() throws IOException {
        stubFor(post(urlEqualTo("/login"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"token\": \"" + TOKEN + "\"\n" +
                                "}")));

        Response<TokenResponse> response = testInstance.login();

        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body().getToken()).isEqualTo(TOKEN);
    }

    @Test
    public void fails() throws IOException {
        stubFor(post(urlEqualTo("/login"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(400)));

        Response<TokenResponse> response = testInstance.login();

        assertThat(response.isSuccessful()).isFalse();
    }

    @Test
    public void timesOut() throws Exception {
        stubFor(post(urlEqualTo("/login"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(CONNECTION_RESET_BY_PEER)));
    }

}