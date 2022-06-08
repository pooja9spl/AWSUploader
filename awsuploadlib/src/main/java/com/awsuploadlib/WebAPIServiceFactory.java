package com.awsuploadlib;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebAPIServiceFactory {
    private static final int HTTP_READ_TIMEOUT = 25;
    private static final int HTTP_CONNECT_TIMEOUT = 6;
    private static final int HTTP_WRITE_TIMEOUT = 25;

    private static WebAPIServiceFactory INSTANCE;

    public static WebAPIServiceFactory newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WebAPIServiceFactory();
        }
        return INSTANCE;
    }

    public WebAPIService makeServiceFactory2() {
        return makeServiceFactory(makeOkHttpClientForSignedUrl());
    }

    private WebAPIService makeServiceFactory(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(WebAPIService.class);
    }

    public OkHttpClient makeOkHttpClientForSignedUrl() {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient().newBuilder();
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.MINUTES);
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT, TimeUnit.MINUTES);
        httpClientBuilder.writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.MINUTES);
        httpClientBuilder.interceptors().add(new Interceptor() {
                                                 @Override
                                                 public Response intercept(@NonNull Chain chain) throws IOException {
                                                     Request original = chain.request();
                                                     // Customize the request
                                                     Request request = original.newBuilder()
                                                             .method(original.method(), original.body())
                                                             .build();

                                                     // Customize or return the response
                                                     return chain.proceed(request);
                                                 }
                                             }
        );


        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                ResponseBody responseBody = response.body();
                String rawJson = responseBody.string();
                try {

                    if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//                        Toast.makeText(context, response.headers().get("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return response.newBuilder()
                        .body(ResponseBody.create(responseBody.contentType(), rawJson)).build();
            }
        });

        return httpClientBuilder.build();
    }

}
