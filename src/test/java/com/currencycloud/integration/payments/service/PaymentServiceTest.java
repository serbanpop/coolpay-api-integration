package com.currencycloud.integration.payments.service;


import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.response.CreatePaymentResponse;
import com.currencycloud.integration.payments.response.PaymentsResponse;
import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.rest.EntityExtractor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Response;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.function.Function;

import static java.lang.Boolean.TRUE;
import static java.math.BigDecimal.TEN;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {

    private static final String ID = "id";
    private static final String OTHER_ID = "other_id";

    private static final Recipient RECIPIENT = new Recipient();
    private static final BigDecimal AMOUNT = TEN;
    private static final String CURRENCY = "GBP";
    private static final String TOKEN = "token";

    private static final Payment CLIENT_PAYMENT = mock(Payment.class);
    private static final Payment API_PAYMENT = mock(Payment.class);

    private static final Response<CreatePaymentResponse> CREATE_PAYMENT_RESPONSE = success(mock(CreatePaymentResponse.class));
    private static final Response<PaymentsResponse> PAYMENTS_RESPONSE = success(mock(PaymentsResponse.class));

    @Mock
    private PaymentClient client;

    @Mock
    private EntityExtractor extractor;

    @InjectMocks
    private PaymentService testInstance;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        given(client.create(any(Payment.class), eq(TOKEN))).willReturn(CREATE_PAYMENT_RESPONSE);
        given(client.list(eq(TOKEN))).willReturn(PAYMENTS_RESPONSE);

        given(extractor.entityFrom(eq(PAYMENTS_RESPONSE), any(Function.class))).willReturn(singletonList(API_PAYMENT));
    }

    @Test(expected = RestClientException.class)
    public void sendMoneyThrowsExceptionWhenClientCallFails() throws Exception {
        given(client.create(any(Payment.class), anyString())).willThrow(new IOException());

        testInstance.sendMoney(RECIPIENT, AMOUNT, CURRENCY, TOKEN);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMoneyReturnsPayment() throws Exception {
        given(extractor.entityFrom(any(Response.class), any(Function.class))).willReturn(API_PAYMENT);

        final Payment result = testInstance.sendMoney(RECIPIENT, AMOUNT, CURRENCY, TOKEN);

        assertThat(result).isEqualTo(API_PAYMENT);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void paymentIsSuccessfulWhenPaymentExistsAndIsPaid() throws Exception {
        givenPaymentIdsMatch();
        given(API_PAYMENT.isSuccessful()).willReturn(TRUE);

        final boolean result = testInstance.isSuccessful(CLIENT_PAYMENT, TOKEN);

        assertThat(result).isTrue();
    }

    @Test
    public void paymentIsUnsuccessfulWhenPaymentDoesNotExist() throws Exception {
        givenPaymentIdsMatch();

        final boolean result = testInstance.isSuccessful(CLIENT_PAYMENT, TOKEN);

        assertThat(result).isFalse();
    }

    @Test
    public void paymentIsUnsuccessfulWhenPaymentExistsAndStatusIsNotPaid() throws Exception {
        givenPaymentIdsDoNotMatch();

        final boolean result = testInstance.isSuccessful(CLIENT_PAYMENT, TOKEN);

        assertThat(result).isFalse();
    }

    @Test(expected = RestClientException.class)
    public void isSuccessfulThrowsExceptionWhenClientCallFails() throws Exception {
        given(client.list(eq(TOKEN))).willThrow(new IOException());

        testInstance.isSuccessful(CLIENT_PAYMENT, TOKEN);
    }

    private void givenPaymentIdsMatch() {
        given(API_PAYMENT.getId()).willReturn(ID);
        given(CLIENT_PAYMENT.getId()).willReturn(ID);
    }

    private void givenPaymentIdsDoNotMatch() {
        given(CLIENT_PAYMENT.getId()).willReturn(ID);
        given(API_PAYMENT.getId()).willReturn(OTHER_ID);
    }

    @After
    public void tearDown() throws Exception {
        reset(API_PAYMENT, CLIENT_PAYMENT);
    }
}