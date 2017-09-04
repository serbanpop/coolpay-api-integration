package com.currencycloud.integration.recipients.response;

import com.currencycloud.integration.recipients.Recipient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CreateRecipientResponse {

    private Recipient recipient;

}