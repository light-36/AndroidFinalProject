package com.app.nasamarsrover.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

/**
 * Utility class for handling theme and locale operations.
 * This class provides methods for changing theme and locale settings.
 */
public class ThemeUtils {

    /**
     * Set the app theme based on the dark mode preference
     * 
     * @param darkModeEnabled True to enable dark mode, false to disable
     */
    public static void setAppTheme(boolean darkModeEnabled) {
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
    
    /**
     * Check if dark mode is currently enabled
     * 
     * @param context The context
     * @return True if dark mode is enabled, false otherwise
     */
    public static boolean isDarkModeEnabled(Context context) {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & 
                Configuration.UI_MODE_NIGHT_MASK;
        return nightModeFlags == Configuration.UI_MODE_NIGHT_YES;
    }
    
    /**
     * Set the app locale
     * 
     * @param context The context
     * @param languageCode The language code (e.g., "en" for English, "fr" for French)
     * @param countryCode The country code (e.g., "US" for United States, "CA" for Canada)
     */
    public static void setAppLocale(Context context, String languageCode, String countryCode) {
        Locale locale = new Locale(languageCode, countryCode);
        Locale.setDefault(locale);
        
        Configuration config = new Configuration(context.getResources().getConfiguration());
        config.setLocale(locale);
        
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }
    
    /**
     * Get the current app locale
     * 
     * @param context The context
     * @return The current locale
     */
    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            return context.getResources().getConfiguration().locale;
        }
    }
    
    /**
     * Recreate the activity to apply theme or locale changes
     * 
     * @param activity The activity to recreate
     */
    public static void recreateActivity(Activity activity) {
        if (activity != null) {
            activity.recreate();
        }
    }
}
