package com.app.nasamarsrover.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.app.nasamarsrover.data.model.NasaImage;
import com.app.nasamarsrover.data.repository.NasaRepository;

import java.util.List;

/**
 * ViewModel for the SavedImagesActivity.
 * This class provides data to the UI and handles business logic.
 */
public class SavedImagesViewModel extends AndroidViewModel {
    
    private final NasaRepository repository;
    private final LiveData<List<NasaImage>> allImages;
    
    /**
     * Constructor for SavedImagesViewModel
     * 
     * @param application The application
     */
    public SavedImagesViewModel(@NonNull Application application) {
        super(application);
        repository = new NasaRepository(application);
        allImages = repository.getAllImages();
    }
    
    /**
     * Get all saved NASA images
     * 
     * @return LiveData list of all NASA images
     */
    public LiveData<List<NasaImage>> getAllImages() {
        return allImages;
    }
    
    /**
     * Insert a NASA image into the database
     * 
     * @param nasaImage The NASA image to insert
     */
    public void insert(NasaImage nasaImage) {
        repository.saveImage(nasaImage, new NasaRepository.DatabaseOperationCallback() {
            @Override
            public void onSuccess() {
                // Image inserted successfully
            }
            
            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });
    }
    
    /**
     * Delete a NASA image from the database
     * 
     * @param nasaImage The NASA image to delete
     */
    public void delete(NasaImage nasaImage) {
        repository.deleteImage(nasaImage, new NasaRepository.DatabaseOperationCallback() {
            @Override
            public void onSuccess() {
                // Image deleted successfully
            }
            
            @Override
            public void onError(String errorMessage) {
                // Handle error
            }
        });
    }
}
