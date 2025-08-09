package com.app.nasamarsrover.data.api;

import com.app.nasamarsrover.data.model.NasaImage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit service interface for NASA API calls.
 * This interface defines methods for accessing the NASA API.
 */
public interface NasaApiService {
    
    /**
     * Get the Astronomy Picture of the Day (APOD) for a specific date
     * 
     * @param apiKey The API key for NASA API
     * @param date The date in YYYY-MM-DD format
     * @return A Call object containing the NASA image data
     */
    @GET("planetary/apod")
    Call<NasaImage> getImageOfTheDay(
            @Query("api_key") String apiKey,
            @Query("date") String date
    );
}
