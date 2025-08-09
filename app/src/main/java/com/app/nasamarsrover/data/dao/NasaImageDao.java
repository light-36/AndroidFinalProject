package com.app.nasamarsrover.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.app.nasamarsrover.data.model.NasaImage;

import java.util.List;

/**
 * Data Access Object (DAO) for NasaImage entities.
 * This interface defines methods for accessing the nasa_images table in the database.
 */
@Dao
public interface NasaImageDao {
    
    /**
     * Insert a new NASA image into the database
     * 
     * @param nasaImage The NASA image to insert
     * @return The row ID of the inserted image
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(NasaImage nasaImage);
    
    /**
     * Get all NASA images from the database
     * 
     * @return LiveData list of all NASA images
     */
    @Query("SELECT * FROM nasa_images ORDER BY date DESC")
    LiveData<List<NasaImage>> getAllImages();
    
    /**
     * Get a NASA image by its date
     * 
     * @param date The date of the image to get
     * @return The NASA image with the specified date, or null if not found
     */
    @Query("SELECT * FROM nasa_images WHERE date = :date LIMIT 1")
    NasaImage getImageByDate(String date);
    
    /**
     * Check if a NASA image with the specified date exists in the database
     * 
     * @param date The date to check
     * @return True if an image with the specified date exists, false otherwise
     */
    @Query("SELECT EXISTS(SELECT 1 FROM nasa_images WHERE date = :date LIMIT 1)")
    boolean imageExists(String date);
    
    /**
     * Delete a NASA image from the database
     * 
     * @param nasaImage The NASA image to delete
     */
    @Delete
    void delete(NasaImage nasaImage);
    
    /**
     * Delete a NASA image by its ID
     * 
     * @param id The ID of the image to delete
     */
    @Query("DELETE FROM nasa_images WHERE id = :id")
    void deleteById(long id);
}
