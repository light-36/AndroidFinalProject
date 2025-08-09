package com.app.nasamarsrover.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room database for the NASA Image of the Day app.
 * This database stores saved NASA images.
 */
@Database(entities = {NasaImage.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "nasa_images_db";
    private static AppDatabase instance;

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

    /**
     * Get the DAO for NASA images
     * 
     * @return The NASA image DAO
     */
    public abstract NasaImageDao nasaImageDao();
}
