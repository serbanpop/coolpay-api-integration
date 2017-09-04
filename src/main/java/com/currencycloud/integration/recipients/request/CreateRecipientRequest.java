package com.currencycloud.integration.recipients.request;

import com.currencycloud.integration.recipients.Recipient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRecipientRequest {

    private Recipient recipient;

}