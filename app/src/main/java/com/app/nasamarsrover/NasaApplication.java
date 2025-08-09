package com.app.nasamarsrover;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

import com.app.nasamarsrover.util.SharedPreferencesManager;

import java.util.Locale;

/**
 * Application class for the NASA Image of the Day app.
 * This class initializes app-wide settings and configurations.
 */
public class NasaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        
        // Initialize theme based on preferences
        SharedPreferencesManager preferencesManager = SharedPreferencesManager.getInstance(this);
        if (preferencesManager.isDarkModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        
        // Initialize language based on preferences
        setLocale(this, preferencesManager.getLanguage());
    }
    
    /**
     * Set the app locale
     * 
     * @param context The context
     * @param languageCode The language code (e.g., "en", "fr")
     */
    public static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        
        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
}
