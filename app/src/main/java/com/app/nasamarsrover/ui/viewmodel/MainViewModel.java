package com.app.nasamarsrover.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.nasamarsrover.api.NasaResponse;
import com.app.nasamarsrover.data.NasaImage;
import com.app.nasamarsrover.repository.NasaRepository;
import com.app.nasamarsrover.util.DateUtils;
import com.app.nasamarsrover.util.NetworkUtils;
import com.app.nasamarsrover.util.SharedPreferencesManager;

/**
 * ViewModel for the MainActivity.
 * This class handles data operations for the MainActivity.
 */
public class MainViewModel extends AndroidViewModel {

    private final NasaRepository repository;
    private final SharedPreferencesManager preferencesManager;
    private final MutableLiveData<NasaResponse> nasaImageLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loadingLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> imageSavedLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> imageExistsLiveData = new MutableLiveData<>();

    /**
     * Constructor for the MainViewModel
     * 
     * @param application The application
     */
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new NasaRepository(application);
        preferencesManager = SharedPreferencesManager.getInstance(application);
    }

    /**
     * Get the NASA image for a specific date
     * 
     * @param date The date in YYYY-MM-DD format
     */
    public void getNasaImage(String date) {
        if (!NetworkUtils.isNetworkAvailable(getApplication())) {
            errorLiveData.setValue("No internet connection");
            return;
        }
        
        if (!DateUtils.isValidDate(date)) {
            errorLiveData.setValue("Invalid date format");
            return;
        }
        
        if (DateUtils.isFutureDate(date)) {
            errorLiveData.setValue("Cannot search for future dates");
            return;
        }
        
        if (DateUtils.isBeforeApiStartDate(date)) {
            errorLiveData.setValue("NASA APOD API only has images from June 16, 1995 onwards");
            return;
        }
        
        loadingLiveData.setValue(true);
        
        repository.getNasaImageByDate(date, new NasaRepository.NasaImageCallback() {
            @Override
            public void onSuccess(NasaResponse nasaResponse) {
                nasaImageLiveData.setValue(nasaResponse);
                loadingLiveData.setValue(false);
                preferencesManager.saveLastViewedDate(date);
                checkIfImageExists(date);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
                loadingLiveData.setValue(false);
            }
        });
    }

    /**
     * Save the current NASA image to the database
     */
    public void saveCurrentImage() {
        NasaResponse nasaResponse = nasaImageLiveData.getValue();
        if (nasaResponse == null) {
            errorLiveData.setValue("No image to save");
            return;
        }
        
        repository.saveNasaImage(nasaResponse, new NasaRepository.DatabaseCallback() {
            @Override
            public void onSuccess() {
                imageSavedLiveData.setValue(true);
                imageExistsLiveData.setValue(true);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.setValue(errorMessage);
            }
        });
    }

    /**
     * Check if an image exists in the database
     * 
     * @param date The date of the image
     */
    public void checkIfImageExists(String date) {
        repository.imageExists(date, imageExistsLiveData::setValue);
    }

    /**
     * Get the LiveData for the NASA image
     * 
     * @return The NASA image LiveData
     */
    public LiveData<NasaResponse> getNasaImageLiveData() {
        return nasaImageLiveData;
    }

    /**
     * Get the LiveData for errors
     * 
     * @return The error LiveData
     */
    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    /**
     * Get the LiveData for loading state
     * 
     * @return The loading state LiveData
     */
    public LiveData<Boolean> getLoadingLiveData() {
        return loadingLiveData;
    }

    /**
     * Get the LiveData for image saved state
     * 
     * @return The image saved state LiveData
     */
    public LiveData<Boolean> getImageSavedLiveData() {
        return imageSavedLiveData;
    }

    /**
     * Get the LiveData for image exists state
     * 
     * @return The image exists state LiveData
     */
    public LiveData<Boolean> getImageExistsLiveData() {
        return imageExistsLiveData;
    }

    /**
     * Reset the image saved state
     */
    public void resetImageSavedState() {
        imageSavedLiveData.setValue(false);
    }
}
