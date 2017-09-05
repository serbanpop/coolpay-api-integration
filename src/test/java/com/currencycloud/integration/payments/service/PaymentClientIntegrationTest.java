package com.currencycloud.integration.payments.service;

import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.response.CreatePaymentResponse;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.http.Fault.CONNECTION_RESET_BY_PEER;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentClientIntegrationTest {

    private static final String TOKEN = "aff06fec-e041-4994-849e-223f0569e0bc";

    private static final BigDecimal AMOUNT = valueOf(10.5);
    private static final String CURRENCY = "GBP";
    private static final String RECIPIENT_ID = "6e7b146e-5957-11e6-8b77-86f30ca893d3";
    private static final String STATUS = "processing";
    private static final String PAYMENT_ID = "31db334f-9ac0-42cb-804b-09b2f899d4d2";


    private PaymentClient testInstance = new PaymentClient();

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void isSuccessful() throws IOException {
        stubFor(post(urlEqualTo("/payments"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withHeader("Authorization", equalTo("Bearer " + TOKEN))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\n" +
                                "  \"payment\": {\n" +
                                "    \"id\": \"" + PAYMENT_ID + "\",\n" +
                                "    \"amount\": \"" + AMOUNT + "\",\n" +
                                "    \"currency\": \"" + CURRENCY + "\",\n" +
                                "    \"recipient_id\": \"" + RECIPIENT_ID + "\",\n" +
                                "    \"status\": \"" + STATUS + "\"\n" +
                                "  }\n" +
                                "}")));

        final Response<CreatePaymentResponse> response = testInstance.create(new Payment(AMOUNT, CURRENCY, RECIPIENT_ID), TOKEN);


        assertThat(response.isSuccessful()).isTrue();

        final Payment responsePayment = response.body().getPayment();
        assertThat(responsePayment.getId()).isEqualTo(PAYMENT_ID);
        assertThat(responsePayment.getAmount()).isEqualTo(AMOUNT);
        assertThat(responsePayment.getCurrency()).isEqualTo(CURRENCY);
        assertThat(responsePayment.getRecipientId()).isEqualTo(RECIPIENT_ID);
        assertThat(responsePayment.getStatus()).isEqualTo(STATUS);
    }

    @Test
    public void fails() throws IOException {
        stubFor(post(urlEqualTo("/payments"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withStatus(400)));

        Response<CreatePaymentResponse> response = testInstance.create(new Payment(AMOUNT, CURRENCY, RECIPIENT_ID), TOKEN);

        assertThat(response.isSuccessful()).isFalse();
    }

    @Test
    public void timesOut() throws Exception {
        stubFor(post(urlEqualTo("/payments"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withFault(CONNECTION_RESET_BY_PEER)));
    }

}