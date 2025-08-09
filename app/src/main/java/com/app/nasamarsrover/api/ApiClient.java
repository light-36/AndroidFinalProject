package com.app.nasamarsrover.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API client for the NASA API.
 * This class provides a singleton instance of the Retrofit client for making API calls.
 */
public class ApiClient {

    private static final String BASE_URL = "https://api.nasa.gov/";
    private static Retrofit retrofit = null;

    /**
     * Get the singleton instance of the Retrofit client
     * 
     * @return The Retrofit client
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Create a logging interceptor for debugging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            // Create an OkHttpClient with the logging interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            // Create the Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
