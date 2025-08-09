package com.app.nasamarsrover.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit service interface for the NASA API.
 * This interface defines the API endpoints for the NASA APOD (Astronomy Picture of the Day) API.
 */
public interface NasaApiService {

    /**
     * Get the Astronomy Picture of the Day for a specific date
     * 
     * @param apiKey The NASA API key
     * @param date The date in YYYY-MM-DD format
     * @return A Call object with the NASA APOD response
     */
    @GET("planetary/apod")
    Call<NasaResponse> getApod(
            @Query("api_key") String apiKey,
            @Query("date") String date
    );
}
