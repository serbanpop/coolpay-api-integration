package com.currencycloud.integration.payments.service;

import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.request.CreatePaymentRequest;
import com.currencycloud.integration.payments.response.CreatePaymentResponse;
import com.currencycloud.integration.payments.response.PaymentsResponse;
import com.currencycloud.integration.payments.service.PaymentClient.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class PaymentClientTest {

    private static final String TOKEN = "token";

    @Mock
    private Payment payment;

    @Mock
    private Call<CreatePaymentResponse> createPaymentResponseCall;

    @Mock
    private Call<PaymentsResponse> listPaymentsResponseCall;

    @Mock
    private HttpClient httpClient;

    private Response<CreatePaymentResponse> createPaymentResponse = success(mock(CreatePaymentResponse.class));
    private Response<PaymentsResponse> listPaymentsResponse = success(mock(PaymentsResponse.class));

    @Spy
    private PaymentClient testInstance = new PaymentClient();

    @Before
    public void setUp() throws Exception {
        given(testInstance.client(eq(TOKEN))).willReturn(httpClient);

        given(httpClient.create(any(CreatePaymentRequest.class))).willReturn(createPaymentResponseCall);
        given(createPaymentResponseCall.execute()).willReturn(createPaymentResponse);

        given(httpClient.list()).willReturn(listPaymentsResponseCall);
        given(listPaymentsResponseCall.execute()).willReturn(listPaymentsResponse);
    }

    @Test
    public void createPaymentReturnsResponse() throws IOException {
        final Response<CreatePaymentResponse> response = testInstance.create(payment, TOKEN);

        assertThat(response).isEqualTo(createPaymentResponse);
    }

    @Test
    public void createPaymentInvokesHttpClient() throws Exception {
        testInstance.create(payment, TOKEN);

        then(httpClient).should().create(any(CreatePaymentRequest.class));
    }

    @Test
    public void listPaymentsReturnsResponse() throws IOException {
        final Response<PaymentsResponse> response = testInstance.list(TOKEN);

        assertThat(response).isEqualTo(listPaymentsResponse);
    }


    @Test
    public void listPaymentInvokesHttpClient() throws Exception {
        testInstance.list(TOKEN);

        then(httpClient).should().list();
    }
}