package com.currencycloud.integration.authentication.service;

import com.currencycloud.integration.authentication.request.TokenRequest;
import com.currencycloud.integration.authentication.response.TokenResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.io.IOException;

import static com.currencycloud.rest.RetrofitFactory.retrofit;

public class LoginClient {

    private static final String USERNAME = "SherbanP";
    private static final String API_KEY = "FFE44382E8AFA95F";

    public Response<TokenResponse> login() throws IOException {
        return client()
                .login(new TokenRequest(USERNAME, API_KEY))
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
