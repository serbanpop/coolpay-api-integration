package com.currencycloud.integration.recipients.service;

import com.currencycloud.integration.recipients.request.CreateRecipientRequest;
import com.currencycloud.integration.recipients.response.CreateRecipientResponse;
import com.currencycloud.integration.recipients.response.RecipientsResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static retrofit2.Response.success;

@RunWith(MockitoJUnitRunner.class)
public class RecipientClientTest {

    private static final String TOKEN = "token";

    private static final String RECIPIENT_NAME = "Bill";

    @Mock
    private Call<CreateRecipientResponse> createRecipientResponseCall;

    @Mock
    private Call<RecipientsResponse> recipientsResponseCall;

    @Mock
    private RecipientClient.HttpClient httpClient;

    private Response<CreateRecipientResponse> createRecipientResponse = success(mock(CreateRecipientResponse.class));
    private Response<RecipientsResponse> recipientsResponse = success(mock(RecipientsResponse.class));

    @Spy
    private RecipientClient testInstance = new RecipientClient();

    @Before
    public void setUp() throws Exception {
        given(testInstance.client(eq(TOKEN))).willReturn(httpClient);

        given(httpClient.create(any(CreateRecipientRequest.class))).willReturn(createRecipientResponseCall);
        given(createRecipientResponseCall.execute()).willReturn(createRecipientResponse);

        given(httpClient.list()).willReturn(recipientsResponseCall);
        given(httpClient.find(anyString())).willReturn(recipientsResponseCall);
        given(recipientsResponseCall.execute()).willReturn(recipientsResponse);

    }

    @Test
    public void createRecipientReturnsResponse() throws IOException {
        final Response<CreateRecipientResponse> response = testInstance.create(RECIPIENT_NAME, TOKEN);

        assertThat(response).isEqualTo(createRecipientResponse);
    }

    @Test
    public void createRecipientInvokesHttpClient() throws Exception {
        testInstance.create(RECIPIENT_NAME, TOKEN);

        then(httpClient).should().create(any(CreateRecipientRequest.class));
    }

    @Test
    public void listRecipientsReturnsResponse() throws IOException {
        final Response<RecipientsResponse> response = testInstance.list(TOKEN);

        assertThat(response).isEqualTo(recipientsResponse);
    }

    @Test
    public void listRecipientsInvokesHttpClient() throws Exception {
        testInstance.list(TOKEN);

        then(httpClient).should().list();
    }

    @Test
    public void findRecipientsReturnsResponse() throws IOException {
        final Response<RecipientsResponse> response = testInstance.findBy(RECIPIENT_NAME, TOKEN);

        assertThat(response).isEqualTo(recipientsResponse);
    }

    @Test
    public void findRecipientsInvokesHttpClient() throws Exception {
        testInstance.findBy(RECIPIENT_NAME, TOKEN);

        then(httpClient).should().find(RECIPIENT_NAME);
    }

}