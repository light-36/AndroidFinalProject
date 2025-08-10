package com.app.nasamarsrover.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Utility class for managing SharedPreferences.
 * This class provides methods for saving and retrieving user preferences.
 */
public class SharedPreferencesManager {
    
    private static final String PREF_NAME = "nasa_app_preferences";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_THEME = "theme";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_FIRST_RUN = "first_run";
    private static final String KEY_LAST_VIEWED_DATE = "last_viewed_date";
    private static final String KEY_DARK_MODE = "dark_mode";
    
    private static SharedPreferencesManager instance;
    private final SharedPreferences sharedPreferences;
    
    /**
     * Private constructor for SharedPreferencesManager
     * 
     * @param context The application context
     */
    private SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
    
    /**
     * Get the singleton instance of SharedPreferencesManager
     * 
     * @param context The application context
     * @return The singleton instance of SharedPreferencesManager
     */
    public static synchronized SharedPreferencesManager getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPreferencesManager(context.getApplicationContext());
        }
        return instance;
    }
    
    /**
     * Save the NASA API key
     * 
     * @param apiKey The NASA API key
     */
    public void saveApiKey(String apiKey) {
        sharedPreferences.edit().putString(KEY_API_KEY, apiKey).apply();
    }
    
    /**
     * Get the NASA API key
     * 
     * @return The NASA API key, or the default key if not set
     */
    public String getApiKey() {
        return sharedPreferences.getString(KEY_API_KEY, "A3ySEOTVitP15agtj078OgvX7RuB9WRuhd8nsbLf");
    }
    
    /**
     * Save the app theme
     * 
     * @param theme The app theme (e.g., "light", "dark", "system")
     */
    public void saveTheme(String theme) {
        sharedPreferences.edit().putString(KEY_THEME, theme).apply();
    }
    
    /**
     * Get the app theme
     * 
     * @return The app theme, or "system" if not set
     */
    public String getTheme() {
        return sharedPreferences.getString(KEY_THEME, "system");
    }
    
    /**
     * Save the app language
     * 
     * @param language The app language (e.g., "en", "fr")
     */
    public void saveLanguage(String language) {
        sharedPreferences.edit().putString(KEY_LANGUAGE, language).apply();
    }
    
    /**
     * Get the app language
     * 
     * @return The app language, or "en" if not set
     */
    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, "en");
    }
    
    /**
     * Set whether this is the first run of the app
     * 
     * @param isFirstRun True if this is the first run, false otherwise
     */
    public void setFirstRun(boolean isFirstRun) {
        sharedPreferences.edit().putBoolean(KEY_FIRST_RUN, isFirstRun).apply();
    }
    
    /**
     * Check if this is the first run of the app
     * 
     * @return True if this is the first run, false otherwise
     */
    public boolean isFirstRun() {
        return sharedPreferences.getBoolean(KEY_FIRST_RUN, true);
    }
    
    /**
     * Save the last viewed date
     * 
     * @param date The last viewed date in YYYY-MM-DD format
     */
    public void saveLastViewedDate(String date) {
        sharedPreferences.edit().putString(KEY_LAST_VIEWED_DATE, date).apply();
    }
    
    /**
     * Get the last viewed date
     * 
     * @return The last viewed date, or null if not set
     */
    public String getLastViewedDate() {
        return sharedPreferences.getString(KEY_LAST_VIEWED_DATE, null);
    }
    
    /**
     * Set whether dark mode is enabled
     * 
     * @param enabled True if dark mode is enabled, false otherwise
     */
    public void setDarkModeEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, enabled).apply();
    }
    
    /**
     * Check if dark mode is enabled
     * 
     * @return True if dark mode is enabled, false otherwise
     */
    public boolean isDarkModeEnabled() {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false);
    }
    
    /**
     * Clear all preferences
     */
    public void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
