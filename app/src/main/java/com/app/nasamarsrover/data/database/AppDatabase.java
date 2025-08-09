package com.app.nasamarsrover.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.app.nasamarsrover.data.dao.NasaImageDao;
import com.app.nasamarsrover.data.model.NasaImage;

/**
 * Room database class for the application.
 * This class provides access to the database and its DAOs.
 */
@Database(entities = {NasaImage.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    
    private static final String DATABASE_NAME = "nasa_image_database";
    private static AppDatabase instance;
    
    /**
     * Get the NasaImageDao for accessing NASA image data
     * 
     * @return The NasaImageDao
     */
    public abstract NasaImageDao nasaImageDao();
    
    /**
     * Get the singleton instance of the database
     * 
     * @param context The application context
     * @return The singleton instance of the database
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
