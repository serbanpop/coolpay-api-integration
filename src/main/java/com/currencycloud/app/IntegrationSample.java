package com.currencycloud.app;

import com.currencycloud.integration.authentication.service.LoginClient;
import com.currencycloud.integration.authentication.service.LoginService;
import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.service.PaymentClient;
import com.currencycloud.integration.payments.service.PaymentService;
import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.integration.recipients.service.RecipientClient;
import com.currencycloud.integration.recipients.service.RecipientService;
import com.currencycloud.rest.EntityExtractor;

import static java.math.BigDecimal.TEN;

public class IntegrationSample {

    private final EntityExtractor entityExtractor;
    private final LoginService loginService;
    private final PaymentService paymentService;
    private final RecipientService recipientService;

    public IntegrationSample() {
        entityExtractor = new EntityExtractor();
        loginService = new LoginService(new LoginClient(), entityExtractor);
        paymentService = new PaymentService(new PaymentClient(), entityExtractor);
        recipientService = new RecipientService(new RecipientClient(), entityExtractor);
    }

    public static void main(String[] args) {
        final IntegrationSample app = new IntegrationSample();

        final String token = app.loginService.login();
        final Recipient recipient = app.recipientService.addRecipient("Leopold", token);
        final Payment payment = app.paymentService.sendMoney(recipient, TEN,"GBP", token);
        final boolean isPaymentSuccessful = app.paymentService.isSuccessful(payment, token);
    }

}
