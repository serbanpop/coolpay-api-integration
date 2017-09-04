package com.currencycloud.integration.payments.response;

import com.currencycloud.integration.payments.Payment;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PaymentsResponse {

    private List<Payment> payments;

}
