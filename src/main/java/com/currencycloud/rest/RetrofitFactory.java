package com.currencycloud.rest;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.currencycloud.integration.PropertiesProvider.get;

public class RetrofitFactory {

    public static Retrofit retrofit() {
        return builder().build();
    }

    public static Retrofit retrofitWith(String token) {
        return builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(chain -> {
                            final Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("Authorization", "Bearer " + token)
                                    .build();
                            return chain.proceed(request);
                        })
                        .build())
                .build();
    }

    private static Retrofit.Builder builder() {
        return new Retrofit.Builder()
                .baseUrl(get("coolpay.url.base"))
                .addConverterFactory(JacksonConverterFactory.create());
    }

}
