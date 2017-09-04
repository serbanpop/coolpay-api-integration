package com.currencycloud.integration.recipients.service;

import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.integration.recipients.response.CreateRecipientResponse;
import com.currencycloud.rest.EntityExtractor;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class RecipientService {

    private final RecipientClient client;
    private final EntityExtractor extractor;

    public Recipient addRecipient(String name, String token) {
        final Recipient recipient = new Recipient(name);
        try {
            return extractor.entityFrom(client.create(name, token), CreateRecipientResponse::getRecipient);
        } catch (IOException e) {
            throw new RestClientException("Failed to create recipient " + recipient, e);
        }
    }

}
