package com.currencycloud.integration.payments.response;

import com.currencycloud.integration.payments.Payment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreatePaymentResponse {

    private Payment payment;

}
