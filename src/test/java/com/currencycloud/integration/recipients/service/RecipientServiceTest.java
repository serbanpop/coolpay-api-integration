package com.currencycloud.integration.recipients.service;

import com.currencycloud.integration.exception.RestClientException;
import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.integration.recipients.response.CreateRecipientResponse;
import com.currencycloud.rest.EntityExtractor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Response;

import java.io.IOException;
import java.util.function.Function;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class RecipientServiceTest {

    private static final String ID = "id";

    private static final String RECIPIENT_NAME = "Andy";
    private static final String TOKEN = "token";

    private static final Recipient API_RECIPIENT = mock(Recipient.class);

    private static final Response<CreateRecipientResponse> CREATE_RECIPIENT_RESPONSE = success(mock(CreateRecipientResponse.class));

    @Mock
    private RecipientClient client;

    @Mock
    private EntityExtractor extractor;

    @InjectMocks
    private RecipientService testInstance;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() throws Exception {
        given(client.create(anyString(), eq(TOKEN))).willReturn(CREATE_RECIPIENT_RESPONSE);

        given(extractor.entityFrom(eq(CREATE_RECIPIENT_RESPONSE), any(Function.class))).willReturn(singletonList(API_RECIPIENT));
    }

    @Test(expected = RestClientException.class)
    public void sendMoneyThrowsExceptionWhenClientCallFails() throws Exception {
        given(client.create(anyString(), anyString())).willThrow(new IOException());

        testInstance.addRecipient(RECIPIENT_NAME, TOKEN);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void sendMoneyReturnsPayment() throws Exception {
        given(extractor.entityFrom(any(Response.class), any(Function.class))).willReturn(API_RECIPIENT);

        final Recipient recipient = testInstance.addRecipient(RECIPIENT_NAME, TOKEN);

        assertThat(recipient).isEqualTo(API_RECIPIENT);
    }

}