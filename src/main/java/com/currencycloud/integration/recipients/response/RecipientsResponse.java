package com.currencycloud.integration.recipients.response;

import com.currencycloud.integration.recipients.Recipient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class RecipientsResponse {

    private List<Recipient> recipients = new ArrayList<>();

}
