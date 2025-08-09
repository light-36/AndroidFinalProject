package com.app.nasamarsrover.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API Client for creating Retrofit service instances.
 * This class provides a singleton instance of the Retrofit service.
 */
public class ApiClient {
    private static final String BASE_URL = "https://api.nasa.gov/";
    private static Retrofit retrofit = null;
    
    /**
     * Get the singleton instance of the Retrofit client
     * 
     * @return The Retrofit client instance
     */
    public static Retrofit getClient() {
        if (retrofit == null) {
            // Create logging interceptor for debugging
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            
            // Create OkHttpClient with the interceptor
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build();
            
            // Create Retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
    
    /**
     * Get an instance of the NASA API service
     * 
     * @return The NASA API service
     */
    public static NasaApiService getNasaApiService() {
        return getClient().create(NasaApiService.class);
    }
}
