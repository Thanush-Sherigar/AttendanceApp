package com.example.attendanceapp;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;

    // Replace this with your actual Codespace Forwarded URL!
    // Make sure it ends with a "/"
    private static final String BASE_URL = "https://opulent-acorn-xjvxr6jrj4g2p9jx-8080.app.github.dev/";

    public static ApiService getApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create()) // For plain text responses
                    .addConverterFactory(GsonConverterFactory.create())    // For JSON
                    .build();
        }
        return retrofit.create(ApiService.class);
    }
}