package com.currencycloud.integration.recipients.service;

import com.currencycloud.integration.recipients.Recipient;
import com.currencycloud.integration.recipients.request.CreateRecipientRequest;
import com.currencycloud.integration.recipients.response.CreateRecipientResponse;
import com.currencycloud.integration.recipients.response.RecipientsResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.*;

import java.io.IOException;

import static com.currencycloud.rest.RetrofitFactory.retrofitWith;

public class RecipientClient {

    public Response<RecipientsResponse> list(String token) throws IOException {
        return client(token)
                .list()
                .execute();
    }
    public Response<RecipientsResponse> findBy(String name, String token) throws IOException {
        return client(token)
                .find(name)
                .execute();
    }

    public Response<CreateRecipientResponse> create(String name, String token) throws IOException {
        return client(token)
                .create(new CreateRecipientRequest(new Recipient(name)))
                .execute();
    }

    HttpClient client(String token) {
        return retrofitWith(token).create(HttpClient.class);
    }

    interface HttpClient {

        @Headers("Content-Type:application/json")
        @GET("recipients")
        Call<RecipientsResponse> list();

        @Headers("Content-Type:application/json")
        @GET("recipients")
        Call<RecipientsResponse> find(@Query("name") String recipientName);

        @Headers("Content-Type:application/json")
        @POST("recipients")
        Call<CreateRecipientResponse> create(@Body CreateRecipientRequest createRecipientRequest);
    }
}
