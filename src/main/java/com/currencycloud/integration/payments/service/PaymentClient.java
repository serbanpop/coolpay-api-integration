package com.currencycloud.integration.payments.service;

import com.currencycloud.integration.payments.Payment;
import com.currencycloud.integration.payments.request.CreatePaymentRequest;
import com.currencycloud.integration.payments.response.CreatePaymentResponse;
import com.currencycloud.integration.payments.response.PaymentsResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.io.IOException;

import static com.currencycloud.rest.RetrofitFactory.retrofitWith;

public class PaymentClient {

    public Response<CreatePaymentResponse> create(Payment payment, String token) throws IOException {
        return client(token)
                .create(new CreatePaymentRequest(payment))
                .execute();

    }

    public Response<PaymentsResponse> list(String token) throws IOException {
        return client(token)
                .list()
                .execute();
    }

    HttpClient client(String token) {
        return retrofitWith(token).create(HttpClient.class);
    }

    interface HttpClient {

        @Headers("Content-Type:application/json")
        @POST("payments")
        Call<CreatePaymentResponse> create(@Body CreatePaymentRequest request);

        @Headers("Content-Type:application/json")
        @GET("payments")
        Call<PaymentsResponse> list();
    }

}
