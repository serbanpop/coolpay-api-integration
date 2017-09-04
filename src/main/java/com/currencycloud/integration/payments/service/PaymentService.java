package com.currencycloud.integration.payments.service;

import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.response.CreatePaymentResponse;
import com.currencycloud.integration.payments.response.PaymentsResponse;
import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.rest.EntityExtractor;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;

import static java.lang.String.format;

@AllArgsConstructor
public class PaymentService {

    private final PaymentClient client;
    private final EntityExtractor extractor;

    public Payment sendMoney(Recipient recipient, BigDecimal amount, String currency, String token) {
        final Payment payment = new Payment(amount, currency, recipient.getId());
        try {
            return extractor.entityFrom(client.create(payment, token), CreatePaymentResponse::getPayment);
        } catch (IOException e) {
            throw new RestClientException("Failed to send payment " + payment, e);
        }
    }

    public boolean isSuccessful(Payment payment, String token) {
        try {
            return extractor.entityFrom(client.list(token), PaymentsResponse::getPayments).stream()
                    .filter(p -> p.getId().equals(payment.getId()))
                    .anyMatch(Payment::isSuccessful);
        } catch (IOException e) {
            throw new RestClientException(format("Failed to check if payment %s was successful", payment.getId()), e);
        }
    }

}
