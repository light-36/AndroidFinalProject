package com.app.nasamarsrover.data.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.app.nasamarsrover.data.api.ApiClient;
import com.app.nasamarsrover.data.api.NasaApiService;
import com.app.nasamarsrover.data.database.AppDatabase;
import com.app.nasamarsrover.data.model.NasaImage;
import com.app.nasamarsrover.data.NasaImageDao;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class that handles data operations.
 * This class provides a clean API for the rest of the application to access data.
 */
public class NasaRepository {
    private static final String API_KEY = "DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d"; // Default API key
    private final AppDatabase database;
    private final NasaApiService apiService;
    private final LiveData<List<NasaImage>> allImages;
    private final ExecutorService executorService;
    
    /**
     * Constructor for NasaRepository
     * 
     * @param context The application context
     */
    public NasaRepository(Context context) {
        database = AppDatabase.getInstance(context);
        apiService = ApiClient.getNasaApiService();
        allImages = database.nasaImageDao().getAllImages();
        executorService = Executors.newSingleThreadExecutor();
    }
    
    /**
     * Get all saved NASA images from the database
     * 
     * @return LiveData list of all NASA images
     */
    public LiveData<List<NasaImage>> getAllImages() {
        return allImages;
    }
    
    /**
     * Fetch NASA image of the day from the API for a specific date
     * 
     * @param date The date in YYYY-MM-DD format
     * @param callback The callback to handle the result
     */
    public void fetchImageOfTheDay(String date, final FetchImageCallback callback) {
        apiService.getImageOfTheDay(API_KEY, date).enqueue(new Callback<NasaImage>() {
            @Override
            public void onResponse(Call<NasaImage> call, Response<NasaImage> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onImageFetched(response.body());
                } else {
                    callback.onError("Error: " + response.code());
                }
            }
            
            @Override
            public void onFailure(Call<NasaImage> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
    
    /**
     * Save a NASA image to the database
     * 
     * @param nasaImage The NASA image to save
     * @param callback The callback to handle the result
     */
    public void saveImage(final NasaImage nasaImage, final DatabaseOperationCallback callback) {
        CompletableFuture.runAsync(() -> {
            try {
                database.nasaImageDao().insert(nasaImage);
                new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess());
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError("Failed to save image"));
            }
        }, executorService);
    }
    
    /**
     * Delete a NASA image from the database
     * 
     * @param nasaImage The NASA image to delete
     * @param callback The callback to handle the result
     */
    public void deleteImage(final NasaImage nasaImage, final DatabaseOperationCallback callback) {
        CompletableFuture.runAsync(() -> {
            database.nasaImageDao().delete(nasaImage);
            new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess());
        }, executorService);
    }
    
    /**
     * Check if a NASA image with the specified date exists in the database
     * 
     * @param date The date to check
     * @return True if an image with the specified date exists, false otherwise
     */
    public boolean imageExists(String date) {
        try {
            return CompletableFuture.supplyAsync(() -> database.nasaImageDao().imageExists(date), executorService).get();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Callback interface for database operations
     */
    public interface DatabaseOperationCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    
    /**
     * Callback interface for fetching NASA images
     */
    public interface FetchImageCallback {
        void onImageFetched(NasaImage nasaImage);
        void onError(String errorMessage);
    }
}
