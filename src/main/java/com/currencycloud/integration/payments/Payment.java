package com.currencycloud.integration.payments;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class Payment {

    private String id;

    private BigDecimal amount;

    private String currency;

    @JsonProperty("recipient_id")
    private String recipientId;

    private String status;

    public Payment(BigDecimal amount, String currency, String recipientId) {
        this.amount = amount;
        this.currency = currency;
        this.recipientId = recipientId;
    }

    public boolean isSuccessful() {
        return "paid".equals(status);
    }

}
