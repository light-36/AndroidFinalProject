package com.app.nasamarsrover.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.app.nasamarsrover.api.ApiClient;
import com.app.nasamarsrover.api.NasaApiService;
import com.app.nasamarsrover.api.NasaResponse;
import com.app.nasamarsrover.data.AppDatabase;
import com.app.nasamarsrover.data.NasaImage;
import com.app.nasamarsrover.data.NasaImageDao;
import com.app.nasamarsrover.util.SharedPreferencesManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository class for NASA image data.
 * This class handles data operations between the API and local database.
 */
public class NasaRepository {

    private final NasaApiService nasaApiService;
    private final NasaImageDao nasaImageDao;
    private final SharedPreferencesManager preferencesManager;

    /**
     * Constructor for the NASA repository
     * 
     * @param context The application context
     */
    public NasaRepository(Context context) {
        nasaApiService = ApiClient.getClient().create(NasaApiService.class);
        nasaImageDao = AppDatabase.getInstance(context).nasaImageDao();
        preferencesManager = SharedPreferencesManager.getInstance(context);
    }

    /**
     * Get the NASA image for a specific date from the API
     * 
     * @param date The date in YYYY-MM-DD format
     * @param callback The callback to handle the API response
     */
    public void getNasaImageByDate(String date, final NasaImageCallback callback) {
        String apiKey = preferencesManager.getApiKey();
        
        nasaApiService.getApod(apiKey, date).enqueue(new Callback<NasaResponse>() {
            @Override
            public void onResponse(Call<NasaResponse> call, Response<NasaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NasaResponse nasaResponse = response.body();
                    callback.onSuccess(nasaResponse);
                } else {
                    callback.onError("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NasaResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    /**
     * Save a NASA image to the local database
     * 
     * @param nasaResponse The NASA API response
     * @param callback The callback to handle the database operation
     */
    public void saveNasaImage(final NasaResponse nasaResponse, final DatabaseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Check if the image already exists
                    int count = nasaImageDao.imageExists(nasaResponse.getDate());
                    if (count > 0) {
                        return false;
                    }
                    
                    // Create a new NasaImage entity
                    NasaImage nasaImage = new NasaImage(
                            nasaResponse.getDate(),
                            nasaResponse.getTitle(),
                            nasaResponse.getUrl(),
                            nasaResponse.getHdUrl(),
                            nasaResponse.getExplanation(),
                            nasaResponse.getCopyright(),
                            nasaResponse.getMediaType()
                    );
                    
                    // Insert the image into the database
                    nasaImageDao.insert(nasaImage);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    callback.onSuccess();
                } else {
                    callback.onError("Image already exists or error saving image");
                }
            }
        }.execute();
    }

    /**
     * Get all saved NASA images from the local database
     * 
     * @return A LiveData list of all saved NASA images
     */
    public LiveData<List<NasaImage>> getAllSavedImages() {
        return nasaImageDao.getAllImages();
    }

    /**
     * Delete a NASA image from the local database
     * 
     * @param nasaImage The NASA image to delete
     * @param callback The callback to handle the database operation
     */
    public void deleteNasaImage(final NasaImage nasaImage, final DatabaseCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    nasaImageDao.delete(nasaImage);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    callback.onSuccess();
                } else {
                    callback.onError("Error deleting image");
                }
            }
        }.execute();
    }

    /**
     * Check if a NASA image exists in the local database
     * 
     * @param date The date of the image in YYYY-MM-DD format
     * @param callback The callback to handle the database operation
     */
    public void imageExists(final String date, final ExistsCallback callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                int count = nasaImageDao.imageExists(date);
                return count > 0;
            }

            @Override
            protected void onPostExecute(Boolean exists) {
                callback.onResult(exists);
            }
        }.execute();
    }

    /**
     * Callback interface for NASA API operations
     */
    public interface NasaImageCallback {
        void onSuccess(NasaResponse nasaResponse);
        void onError(String errorMessage);
    }

    /**
     * Callback interface for database operations
     */
    public interface DatabaseCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    /**
     * Callback interface for existence checks
     */
    public interface ExistsCallback {
        void onResult(boolean exists);
    }
}
