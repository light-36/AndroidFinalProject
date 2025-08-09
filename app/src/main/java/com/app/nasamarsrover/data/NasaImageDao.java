package com.app.nasamarsrover.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for NASA images.
 * This interface provides methods for accessing the NASA images database.
 */
@Dao
public interface NasaImageDao {

    /**
     * Insert a NASA image into the database.
     * If the image already exists, it will be replaced.
     * 
     * @param nasaImage The NASA image to insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NasaImage nasaImage);

    /**
     * Delete a NASA image from the database
     * 
     * @param nasaImage The NASA image to delete
     */
    @Delete
    void delete(NasaImage nasaImage);

    /**
     * Get all NASA images from the database, ordered by timestamp (newest first)
     * 
     * @return A LiveData list of all NASA images
     */
    @Query("SELECT * FROM nasa_images ORDER BY timestamp DESC")
    LiveData<List<NasaImage>> getAllImages();

    /**
     * Get a NASA image by date
     * 
     * @param date The date of the image (YYYY-MM-DD)
     * @return The NASA image with the specified date, or null if not found
     */
    @Query("SELECT * FROM nasa_images WHERE date = :date LIMIT 1")
    NasaImage getImageByDate(String date);

    /**
     * Check if a NASA image exists in the database
     * 
     * @param date The date of the image (YYYY-MM-DD)
     * @return True if the image exists, false otherwise
     */
    @Query("SELECT COUNT(*) FROM nasa_images WHERE date = :date")
    int imageExists(String date);

    /**
     * Delete all NASA images from the database
     */
    @Query("DELETE FROM nasa_images")
    void deleteAll();
}
