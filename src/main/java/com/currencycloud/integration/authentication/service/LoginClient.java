package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.request.TokenRequest;
import com.currencycloud.integration.authentication.response.TokenResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.io.IOException;

import static com.currencycloud.integration.PropertiesProvider.get;
import static com.currencycloud.rest.RetrofitFactory.retrofit;

public class LoginClient {

    public Response<TokenResponse> login() throws IOException {
        return client()
                .login(new TokenRequest(get("coolpay.username"), get("coolpay.apikey")))
                .execute();
    }

    HttpClient client() {
        return retrofit().create(HttpClient.class);
    }

    interface HttpClient {

        @Headers("Content-Type:application/json")
        @POST("login")
        Call<TokenResponse> login(@Body TokenRequest tokenRequest);
    }
}
