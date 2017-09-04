package com.currencycloud.integration.payments.request;

import com.currencycloud.integration.payments.Payment;
import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    private Payment payment;

}
